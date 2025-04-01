package com.univolunteer.common.domain.entity;

import com.univolunteer.common.enums.UserRoleEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Users {
    private Long id;                  // 用户ID
    private String username;            // 用户名
    private String password;            // 加密密码
    private String email;               // 邮箱地址
    private String phone;               // 手机号
    private UserRoleEnum role;               // 用户主角色（0志愿者，1招募方，2管理员）
    private Long status;             // 账号状态（1正常，0冻结）
    private LocalDateTime lastLoginTime; // 最后登录时间
    private LocalDateTime createTime;    // 创建时间
    private Long organizationId;  //组织id
}
