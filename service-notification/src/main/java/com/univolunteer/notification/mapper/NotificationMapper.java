package com.univolunteer.notification.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.notification.domain.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
