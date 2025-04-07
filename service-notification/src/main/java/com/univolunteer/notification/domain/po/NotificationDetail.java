package com.univolunteer.notification.domain.po;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class NotificationDetail {
    private String message;          // 通知内容
    private LocalDateTime sentTime;  // 发送时间
    private Long userId; //发件人
    private String username;
    private String activityName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String organizationName;
}
