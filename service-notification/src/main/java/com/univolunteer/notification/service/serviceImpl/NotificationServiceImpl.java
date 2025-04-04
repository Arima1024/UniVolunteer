package com.univolunteer.notification.service.serviceImpl;

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
import com.univolunteer.notification.domain.entity.Announcement;
import com.univolunteer.notification.domain.entity.Notification;
import com.univolunteer.notification.domain.po.NotificationDetail;
import com.univolunteer.notification.mapper.AnnouncementsMapper;
import com.univolunteer.notification.mapper.NotificationMapper;
import com.univolunteer.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        Page<Notification> pageInfo = new Page<>(page, size);
        lambdaQuery().eq(Notification::getUserId, userId).orderByDesc(Notification::getCreateTime).page(pageInfo);
        return Result.ok(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @Override
    public Result getUnreadNotification(Integer page, Integer size) {
        Long userId = UserContext.getUserId();
        Page<Notification> pageInfo = new Page<>(page, size);
        lambdaQuery().eq(Notification::getUserId, userId).eq(Notification::getStatus, 0).orderByDesc(Notification::getCreateTime).page(pageInfo);
        return Result.ok(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @Override
    public Result readNotification(Long id) {
        Notification byId = getById(id);
        if (byId == null) {
            return Result.fail("消息不存在");
        }
        byId.setStatus(1);
        return updateById(byId) ? Result.ok("已读") : Result.fail("已读失败");
    }


    @Override
    public Result getUnreadNotificationCount() {
        //获取该用户的未读消息数量
        long count = lambdaQuery().eq(Notification::getUserId, UserContext.getUserId()).eq(Notification::getStatus, 0).count();
        return Result.ok(Map.of("count", count));
    }

    @Override
    public Result sendAnnouncement(Announcement announcement) {
        announcement.setPublishTime(LocalDateTime.now());
        announcementsMapper.insert(announcement);
        return Result.ok("发送公告成功");
    }

    @Override
    public Result getAnnouncement() {
        return null;
    }

}
