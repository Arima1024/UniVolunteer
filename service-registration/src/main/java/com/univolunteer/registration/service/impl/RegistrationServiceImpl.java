package com.univolunteer.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univolunteer.api.client.ActivityClient;
import com.univolunteer.common.config.JacksonConfig;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.entity.Activity;
import com.univolunteer.common.result.Result;
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

    @Override
    @Transactional
    public Result register(Long activityId) {
        Result activityResult = activityClient.getActivity(activityId);
        if (activityResult.getSuccess()) {
            // 如果请求成功，提取 data（即 Activity 对象）
            ObjectMapper objectMapper = new JacksonConfig().objectMapper();
            Activity activity = null;            //判断是否满人
            activity =  objectMapper.convertValue(activityResult.getData(), Activity.class);

            if (activity.getCurrentSignUpCount() >= activity.getMaxVolunteers()) {
                return Result.fail("活动已满");
            }
            System.out.println("UserContext.getUserId() = " + UserContext.getUserId());
            //判断是否是否报名
            List<Registration> list = lambdaQuery()
                    .eq(Registration::getActivityId, activityId)
                    .eq(Registration::getUserId, UserContext.getUserId())
                    .list();
            if (list!=null&&!list.isEmpty()) {
                return Result.fail("该志愿已报名");
            }
            //判断是否在报名时间范围内
            if (activity.getSignUpStartTime().isAfter(LocalDateTime.now()) || activity.getSignUpEndTime().isBefore(LocalDateTime.now())) {
                return Result.fail("不在报名时间范围内");
            }
        } else {
            // 如果请求失败，返回错误消息
            return Result.fail(activityResult.getErrorMsg());
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
            reason="审核通过";
            //需要去活动服务调用相关接口改变活动报名人数
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
