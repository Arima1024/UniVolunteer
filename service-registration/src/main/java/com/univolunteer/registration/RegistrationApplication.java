package com.univolunteer.registration;

import com.univolunteer.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.univolunteer.registration.mapper")
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,basePackages = "com.univolunteer.api.client")
public class RegistrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationApplication.class, args);
    }

}

