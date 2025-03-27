package com.univolunteer.common.interceptor;

import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.dto.UserInfoDTO;
import com.univolunteer.common.enums.UserRoleEnum;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;




@Slf4j
@Component
public class UserContextInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("userId");
        String username = request.getHeader("username");
        String role = request.getHeader("role");
        if (userId != null && username != null) {
            //将Sting类型 role转成UserRoleEnum类型
            UserRoleEnum roleEnum = UserRoleEnum.valueOf(role);
            UserContext.set(new UserInfoDTO(Long.valueOf(userId), username, roleEnum));
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
