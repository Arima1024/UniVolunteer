package com.univolunteer.common.aspect;

import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.dto.UserInfoDTO;
import com.univolunteer.common.exception.AdminException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecruiterOnlyAspect {

    @Before("@annotation(com.univolunteer.common.annotation.RecruiterOnly)")
    public void checkAdminAccess() {
        UserInfoDTO user = UserContext.get();

        if (!"RECRUITER".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            throw new AdminException("只有招募方才能发放志愿");
        }
    }
}
