package com.univolunteer.common.config;


import com.univolunteer.common.interceptor.UserContextInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;

@Slf4j
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class UserContextInterceptorConfig implements WebMvcConfigurer {

    public UserContextInterceptorConfig() {
        log.info("✅ 加载了 UserContextInterceptorConfig 自动配置");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor())
                .excludePathPatterns("/user/login", "/user/register")
             ;
    }
}
