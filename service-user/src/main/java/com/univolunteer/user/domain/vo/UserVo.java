package com.univolunteer.user.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String role;
    private Integer status;
    private Long organizationId;
    private String organizationName;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
}
