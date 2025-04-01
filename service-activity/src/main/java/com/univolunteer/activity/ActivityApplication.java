package com.univolunteer.activity;

import com.univolunteer.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.univolunteer.activity.mapper")
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,basePackages = "com.univolunteer.api.client")

public class ActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }
}
