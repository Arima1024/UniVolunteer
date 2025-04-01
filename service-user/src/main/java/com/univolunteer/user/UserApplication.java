package com.univolunteer.user;

import com.univolunteer.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
@MapperScan("com.univolunteer.user.mapper")
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,basePackages = "com.univolunteer.api.client")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}

