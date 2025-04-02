package com.servicerecord;

import com.univolunteer.api.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,basePackages = "com.univolunteer.api.client")
public class RecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordApplication.class, args);
    }

}
