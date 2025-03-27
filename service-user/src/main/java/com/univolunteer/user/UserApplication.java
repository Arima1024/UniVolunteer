package com.univolunteer.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
@MapperScan("com.univolunteer.user.mapper")
@SpringBootApplication
public class UserApplication {

    @Value("${spring.datasource.url}")
    private String url;


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}

