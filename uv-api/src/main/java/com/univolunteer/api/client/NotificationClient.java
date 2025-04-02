package com.univolunteer.api.client;

import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-notification")
public interface NotificationClient {
    @PostMapping("/notification")
     Result sendNotification(@RequestBody NotificationDTO notificationDTO);
}
