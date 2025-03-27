package com.univolunteer.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    VOLUNTEER(0),   // 志愿者
    RECRUITER(1),   // 招募方
    ADMIN(2);       // 管理员

    @EnumValue
    private final int code;

    UserRoleEnum(int code) {
        this.code = code;
    }

}
