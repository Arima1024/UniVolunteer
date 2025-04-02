package com.univolunteer.notification.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.result.Result;
import com.univolunteer.notification.domain.entity.Notification;

public interface NotificationService extends IService<Notification> {
    Result sendNotification(NotificationDTO notificationDTO);

    Result getAllNotification(Integer page, Integer size);

    Result getUnreadNotification(Integer page, Integer size);

    Result readNotification(Long id);

    Result getUnreadNotificationCount();
}
