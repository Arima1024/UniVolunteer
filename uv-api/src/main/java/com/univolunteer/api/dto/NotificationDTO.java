package com.univolunteer.api.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long userId;
    private String message;
    private Long senderId;
    private Long activityId;
    private Integer type;
}
