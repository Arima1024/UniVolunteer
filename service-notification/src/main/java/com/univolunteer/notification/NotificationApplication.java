package com.univolunteer.notification;

import com.univolunteer.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.univolunteer.notification.mapper")
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,basePackages = "com.univolunteer.api.client")
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

}

