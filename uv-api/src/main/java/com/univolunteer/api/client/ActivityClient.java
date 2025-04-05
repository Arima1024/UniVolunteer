package com.univolunteer.api.client;

import com.univolunteer.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("service-activity")
public interface ActivityClient {
    @GetMapping("/activity/{activityId}")
    Result getActivity(@PathVariable Long activityId);

    @PostMapping("/activity/signUp/{activityId}")
     Result signUp(@PathVariable Long activityId);

    @PostMapping("/activity/signDown/{activityId}")
    Result signDown(@PathVariable Long activityId);

    @GetMapping("/activity/check/{activityId}")
    Result check(@PathVariable Long activityId);

    @GetMapping("/activity/countByUserId")
    public Result getActivityCountByUserId(@RequestParam Long userId);
}
