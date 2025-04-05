package com.univolunteer.api.client;

import com.univolunteer.common.domain.entity.AuditLog;
import com.univolunteer.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-log")
public interface LogClient {
    @PostMapping("/log")
     Result saveLog(@RequestBody AuditLog auditLog);
}
