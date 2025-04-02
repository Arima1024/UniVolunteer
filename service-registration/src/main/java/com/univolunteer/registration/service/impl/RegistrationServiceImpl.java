package com.univolunteer.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univolunteer.api.client.ActivityClient;
import com.univolunteer.api.client.NotificationClient;
import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.entity.Activity;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.utils.ResultParserUtils;
import com.univolunteer.registration.domain.entity.Registration;
import com.univolunteer.registration.domain.entity.RegistrationHistory;
import com.univolunteer.registration.mapper.RegistrationHistoryMapper;
import com.univolunteer.registration.mapper.RegistrationMapper;
import com.univolunteer.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    private final RegistrationMapper registrationMapper;
    private final RegistrationHistoryMapper registrationHistoryMapper;
    private final ActivityClient activityClient;
    private final NotificationClient notificationClient;
    private final ResultParserUtils resultParserUtils;
    @Override
    @Transactional
    public Result register(Long activityId) {
        Result checkResult = activityClient.check(activityId);
        if (checkResult.getSuccess()) {
            //判断是否是否报名
            List<Registration> list = lambdaQuery()
                    .eq(Registration::getActivityId, activityId)
                    .eq(Registration::getUserId, UserContext.getUserId())
                    .list();
            if (list!=null&&!list.isEmpty()) {
                return Result.fail("该志愿已报名");
            }
        } else {
            // 如果请求失败，返回错误消息
            return Result.fail(checkResult.getErrorMsg());
        }
        Registration registration = new Registration();
        Long userId = UserContext.getUserId();
        registration.setActivityId(activityId);
        registration.setUserId(userId);
        registration.setApplyTime(LocalDateTime.now());
        save(registration);
        RegistrationHistory registrationHistory = new RegistrationHistory();
        registrationHistory.setRegistrationId(registration.getId());
        registrationHistory.setChangeTime(LocalDateTime.now());
        registrationHistory.setChangedBy(userId);
        registrationHistory.setReason("等待审核");
        //插入到历史记录表
        registrationHistoryMapper.insert(registrationHistory);
        //发送信息给招募方
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setActivityId(activityId);
        notificationDTO.setSenderId(UserContext.getUserId());
        Result activityResult = activityClient.getActivity(activityId);
        Activity activity = resultParserUtils.parseData(activityResult, Activity.class);
        notificationDTO.setUserId(activity.getUserId());
        notificationDTO.setMessage("有新的报名，请前往审核");
        notificationDTO.setType(0);
        return Result.ok("报名成功，等待审核");
    }

    @Override
    @Transactional
    public Result check(Long registrationId, Integer status,String reason) {
        //根据registrationId从历史表拿出对应记录
        RegistrationHistory registrationHistory = registrationHistoryMapper.selectOne(
                new QueryWrapper<RegistrationHistory>().eq("registration_id", registrationId)
        );
        Registration registration = getById(registrationId);
        registrationHistory.setStatusFrom(registration.getStatus());
        registrationHistory.setStatusTo(status);
        registrationHistory.setChangeTime(LocalDateTime.now());
        registration.setStatus(status);
        if (status==1){
            reason="恭喜你审核通过";
            //需要去活动服务调用相关接口改变活动报名人数
        }else {
            if (reason==null||reason.isEmpty()){
                return Result.fail("请输入拒绝原因");
            }
        }
        registration.setApprovalTime(LocalDateTime.now());
        registration.setApproverId(UserContext.getUserId());
        updateById(registration);
        registrationHistory.setReason(reason);
        registrationHistory.setChangedBy(UserContext.getUserId());
        LambdaUpdateWrapper<RegistrationHistory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RegistrationHistory::getId, registrationHistory.getId());  // 添加 id 条件
        registrationHistoryMapper.update(registrationHistory,updateWrapper);
        //去活动微服务里面改变对应报名人数
        activityClient.signUp(registration.getActivityId());
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setActivityId(registration.getActivityId());
        notificationDTO.setSenderId(UserContext.getUserId());
        notificationDTO.setUserId(registration.getUserId());
        notificationDTO.setMessage(reason);
        notificationDTO.setType(status);
        notificationClient.sendNotification(notificationDTO);
        return Result.ok("审核成功");
    }

    @Transactional
    @Override
    public Result cancel(Long registrationId,String reason) {
        //获取registrationId对应的registration
        Registration registration = getById(registrationId);
        if (registration==null){
            return Result.fail("报名记录不存在");
        }
        if (registration.getStatus()==1){
            return Result.fail("报名已通过，无法取消");
        }
        //根据registrationId从历史表拿出对应记录
        RegistrationHistory registrationHistory = registrationHistoryMapper.selectOne(
                new QueryWrapper<RegistrationHistory>().eq("registration_id", registrationId)
        );
        if (registrationHistory==null){
            return Result.fail("报名记录不存在");
        }
        if (registration.getStatus()==1){
            Result result = activityClient.signDown(registration.getActivityId());
            if (result.getErrorMsg()!=null){
                return  result;
            }
        }
        registrationHistory.setStatusFrom(registration.getStatus());
        registrationHistory.setStatusTo(3);
        registrationHistory.setChangeTime(LocalDateTime.now());
        registrationHistory.setReason(reason);
        registrationHistory.setChangedBy(UserContext.getUserId());
        LambdaUpdateWrapper<RegistrationHistory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RegistrationHistory::getId, registrationHistory.getId());  // 添加 id 条件
        registrationHistoryMapper.update(registrationHistory,updateWrapper);
        //更新registration
        registration.setStatus(3);
        updateById(registration);
        return Result.ok("取消报名成功");
    }
}
