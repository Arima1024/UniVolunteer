package com.univolunteer.activity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.univolunteer.activity.mapper")
@SpringBootApplication
public class ActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }
}
