package com.univolunteer.common.aspect;

import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.dto.UserInfoDTO;
import com.univolunteer.common.exception.AdminException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminOnlyAspect {

    @Before("@annotation(com.univolunteer.common.annotation.AdminOnly)")
    public void checkAdminAccess() {
        UserInfoDTO user = UserContext.get();
        if (!"ADMIN".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            throw new AdminException("权限不足，仅管理员可访问");
        }
    }

}

