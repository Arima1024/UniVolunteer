package com.univolunteer.notification.domain.po;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class NotificationDetail {
    private String title;
    private String message;          // 通知内容
    private LocalDateTime sentTime;  // 发送时间
    private Map<String, Object> data; // 存储任意数量的对象
}
