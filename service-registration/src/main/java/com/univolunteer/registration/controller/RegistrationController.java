package com.univolunteer.registration.controller;

import com.univolunteer.common.annotation.AdminOnly;
import com.univolunteer.common.annotation.RecruiterOnly;
import com.univolunteer.common.annotation.VolunteerOnly;
import com.univolunteer.common.result.Result;
import com.univolunteer.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    //报名接口
    @VolunteerOnly
    @PostMapping("/register/{activityId}")
    public Result register(@PathVariable Long activityId) {
        return registrationService.register(activityId);
    }

    //审核接口
    @RecruiterOnly
    @PostMapping("/check/{registrationId}")
    public Result check(@RequestParam Integer status,
                        @PathVariable Long registrationId,
                        @RequestParam(required = false) String reason) {
        return registrationService.check(registrationId, status,reason);
    }

    @GetMapping("/recruiter/checkList")
    public Result getCheckList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam Long activityId,
                               @RequestParam(required = false) Integer status) {
        return registrationService.getCheckList(page, size,activityId,status);
    }

    //志愿者自己取消
    @VolunteerOnly
    @PostMapping("/cancel/{registrationId}")
    public Result cancel(@PathVariable Long registrationId,@RequestParam String reason) {
        return registrationService.cancel(registrationId,reason);
    }

    @GetMapping("/byStatus")
    public Result getRegistrationListByStatus(@RequestParam(required = false)Integer status,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return registrationService.getRegistrationListByStatus(status, page, size);
    }

}
