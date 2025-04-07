package com.univolunteer.controller;

import com.univolunteer.common.result.Result;
import com.univolunteer.common.domain.entity.AuditLog;
import com.univolunteer.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class logController {

    private final LogService logService;

    @PostMapping
    public Result saveLog(@RequestBody AuditLog auditLog){
         return logService.addLog(auditLog);
    }

    @GetMapping
    public Result getLog(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size){
        return logService.getLog(page,size);
    }
}
