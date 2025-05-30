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
}
