package com.univolunteer.api.client;

import com.univolunteer.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-record")
public interface RecordClient {

    @GetMapping("/record/{activityId}&{userId}")
    Result getRecord(@PathVariable("activityId") Long activityId, @PathVariable("userId") Long userId);

    @GetMapping("/completedRecords")
    Result getCompletedRecords();

    @PostMapping("/volunteerRecord/add")
    Result addRecord(@RequestParam Long activityId);

}
