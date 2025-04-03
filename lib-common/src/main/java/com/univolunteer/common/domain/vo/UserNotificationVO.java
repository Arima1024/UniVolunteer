package com.univolunteer.common.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserNotificationVO {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String organizationName;
    private LocalDateTime createTime;
    private String status;
    private LocalDateTime lastLoginTime;
    private Double hours;
    private Long count;
}
