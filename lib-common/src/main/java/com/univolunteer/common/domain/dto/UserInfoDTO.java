package com.univolunteer.common.domain.dto;

import com.univolunteer.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long userId;
    private String username;
    private UserRoleEnum role;
}
