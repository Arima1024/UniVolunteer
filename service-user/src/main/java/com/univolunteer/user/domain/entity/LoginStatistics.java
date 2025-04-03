package com.univolunteer.user.domain.entity;

import com.univolunteer.common.enums.UserRoleEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginStatistics {
    private Long id;
    private LocalDateTime LoginDate;
    private Long LoginCount;
    private UserRoleEnum role;
}
