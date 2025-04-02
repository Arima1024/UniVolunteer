package com.univolunteer.common.aspect;

import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.dto.UserInfoDTO;
import com.univolunteer.common.exception.AdminException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VolunteerOnlyAspect {
    @Before("@annotation(com.univolunteer.common.annotation.VolunteerOnly)")
    public void checkAdminAccess() {
        UserInfoDTO user = UserContext.get();

        if (!"VOLUNTEER".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            throw new AdminException("不是志愿者无法参加活动");
        }
    }
}
