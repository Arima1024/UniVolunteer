package com.univolunteer.notification.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.notification.domain.entity.Notification;
import com.univolunteer.notification.mapper.NotificationMapper;
import com.univolunteer.notification.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper,Notification> implements NotificationService {
}
