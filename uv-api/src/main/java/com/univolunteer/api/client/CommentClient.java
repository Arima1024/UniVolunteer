package com.univolunteer.api.client;

import com.univolunteer.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-comment")
public interface CommentClient {

    @PostMapping("/comments/auto-generate/{activityId}/{userId}")
    Result autoGenerateComments(@PathVariable("activityId") Long activityId, @PathVariable("userId") Long userId);
}