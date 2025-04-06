package com.univolunteer.user.domain.dto;

import com.univolunteer.common.enums.UserRoleEnum;

import lombok.Data;


@Data
public class RegisterUserDto {


    private String username;


    private String password;


    private String email;


    private String phone;


    private Integer role;


    private String OrganizationName;
}
