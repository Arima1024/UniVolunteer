package com.univolunteer.notification.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.api.client.ActivityClient;
import com.univolunteer.api.client.UserClient;
import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.entity.Users;
import com.univolunteer.common.domain.vo.ActivityVO;
import com.univolunteer.common.domain.vo.UserNotificationVO;
import com.univolunteer.common.result.Result;

import com.univolunteer.common.utils.ResultParserUtils;
import com.univolunteer.notification.domain.dto.MessageDTO;
import com.univolunteer.notification.domain.entity.Announcement;
import com.univolunteer.notification.domain.entity.Notification;
import com.univolunteer.notification.domain.po.NotificationDetail;
import com.univolunteer.notification.mapper.AnnouncementsMapper;
import com.univolunteer.notification.mapper.NotificationMapper;
import com.univolunteer.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper,Notification> implements NotificationService {

    private final ActivityClient activityClient;

    private final UserClient userClient;

    private final ResultParserUtils resultParserUtils;

    private final AnnouncementsMapper announcementsMapper;


    @Override
    public Result sendNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setUserId(notificationDTO.getUserId());
        notification.setSenderId(notificationDTO.getSenderId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setStatus(0);
        notification.setCreateTime(LocalDateTime.now());
        notification.setActivityId(notificationDTO.getActivityId());

        notification.setType(notificationDTO.getType());

        return save(notification) ? Result.ok("发送成功") : Result.fail("发送失败");
    }

    @Override
    public Result getAllNotification(Integer page, Integer size) {
        //分页查询该用户所有的消息，按照发送时间降序排列，后发的放在前面
        Long userId = UserContext.getUserId();
        System.out.println("userId = " + userId);
        Page<Notification> pageInfo = new Page<>(page, size);
        lambdaQuery().eq(Notification::getUserId, userId).orderByDesc(Notification::getCreateTime).page(pageInfo);
        IPage<MessageDTO> messageDTO = getMessageDTO(page, size, pageInfo);
        return Result.ok(messageDTO.getRecords(), messageDTO.getTotal());
    }

    @Override
    public Result getUnreadNotification(Integer page, Integer size) {
        Long userId = UserContext.getUserId();
        Page<Notification> pageInfo = new Page<>(page, size);
        lambdaQuery().eq(Notification::getUserId, userId).eq(Notification::getStatus, 0).orderByDesc(Notification::getCreateTime).page(pageInfo);
        IPage<MessageDTO> messageDTO = getMessageDTO(page, size, pageInfo);
        return Result.ok(messageDTO.getRecords(), messageDTO.getTotal());
    }
    @Override
    public Result readNotificationDetail(Long id) {
        Notification byId = getById(id);
        if (byId == null) {
            return Result.fail("消息不存在");
        }
        byId.setStatus(1);
        updateById(byId);
        ActivityVO activityVO = resultParserUtils.parseData(activityClient.getActivity(byId.getActivityId()).getData(), ActivityVO.class);
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setActivityName(activityVO.getTitle());
        notificationDetail.setEndTime(activityVO.getEndTime());
        notificationDetail.setStartTime(activityVO.getStartTime());
        notificationDetail.setSentTime(byId.getCreateTime());
        UserNotificationVO userNotificationVO = resultParserUtils.parseData(userClient.getUserByRecord(byId.getSenderId()), UserNotificationVO.class);
        notificationDetail.setMessage(byId.getMessage());
        notificationDetail.setUsername(userNotificationVO.getUsername());
        notificationDetail.setOrganizationName(userNotificationVO.getOrganizationName());
        return Result.ok(notificationDetail);
    }




    @Override
    public Result getUnreadNotificationCount() {
        System.out.println(UserContext.getUserId());
        //获取该用户的未读消息数量
        long count = lambdaQuery().eq(Notification::getUserId, UserContext.getUserId()).eq(Notification::getStatus, 0).count();
        return Result.ok(Map.of("count", count));
    }

    @Override
    public Result sendAnnouncement(Announcement announcement) {
        announcement.setPublishTime(LocalDateTime.now());
        announcement.setUserId(UserContext.getUserId());
        announcementsMapper.insert(announcement);
        if (announcement.getStatus() == 1){
            return Result.ok("发送公告成功");
        }
       if (announcement.getStatus()==0){
           return Result.ok("保存草稿成功");
       }
       return Result.fail("发送公告失败");
    }

    @Override
    public Result getAnnouncement() {
        //时间要在publishTime和endTime之间的，并且role是对应的，status是1的
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        if (UserContext.get().getRole().getCode() != 2){
            queryWrapper.le("publish_time", LocalDateTime.now())
                    .ge("end_time", LocalDateTime.now())
                    .eq("target_role", UserContext.get().getRole().getCode())
                    .eq("status", 1);
        }
        queryWrapper.eq("status", 1);

        List<Announcement> announcements = announcementsMapper.selectList(queryWrapper);
        System.out.println("announcements = " + announcements);
        return Result.ok(announcements);
    }

    @Override
    public Result getDraft() {
        //使用mybatis-plus构造查询条件，找到status=0还有userId
       QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("user_id", UserContext.getUserId()).eq("status", 0);
        List<Announcement> announcements = announcementsMapper.selectList(queryWrapper);
        //删除草稿
        announcements.forEach(announcement -> {
            announcementsMapper.deleteById(announcement.getId());
        });
        return Result.ok(announcements);
    }
    private IPage<MessageDTO> getMessageDTO(int page, int size, Page<Notification> notificationPage) {
         List<MessageDTO> messageDTOList = new ArrayList<>();
         notificationPage.getRecords().forEach(notification -> {
             System.out.println("notification = " + notification);
             MessageDTO messageDTO = new MessageDTO();
             UserNotificationVO userNotificationVO = resultParserUtils.parseData(userClient.getUserByRecord(notification.getSenderId()).getData(), UserNotificationVO.class);
             System.out.println("userNotificationVO = " + userNotificationVO);
             messageDTO.setSendTime(notification.getCreateTime());
             messageDTO.setType(notification.getType());
             messageDTO.setUsername(userNotificationVO.getUsername());
             messageDTO.setId(notification.getId());
             messageDTOList.add(messageDTO);
         });
         Page<MessageDTO> messageDTOPage = new Page<>(page, size);
         messageDTOPage.setRecords(messageDTOList);
         messageDTOPage.setTotal(notificationPage.getTotal());
         messageDTOPage.setPages(notificationPage.getPages());
         messageDTOPage.setCurrent(notificationPage.getCurrent());
         return messageDTOPage;
     }
    }
