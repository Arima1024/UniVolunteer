package com.univolunteer.activity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univolunteer.common.annotation.AdminOnly;
import com.univolunteer.common.annotation.RecruiterOnly;
import com.univolunteer.common.result.Result;
import com.univolunteer.activity.domain.dto.ActivityCreateDTO;
import com.univolunteer.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @RecruiterOnly
    @PostMapping
    public Result createActivity(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("location") String location,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime,
            @RequestParam("signUpStartTime") LocalDateTime signUpStartTime,
            @RequestParam("signUpEndTime") LocalDateTime signUpEndTime,
            @RequestParam("maxVolunteers") Long maxVolunteers,
            @RequestParam("file") MultipartFile file
    ) {
        ActivityCreateDTO dto=new ActivityCreateDTO(title,description,category,location,startTime,endTime,maxVolunteers,signUpStartTime,signUpEndTime);
        return activityService.createActivity(dto, file);
    }

    @GetMapping("/{activityId}")
    public Result getActivity(@PathVariable Long activityId){
        return activityService.getActivity(activityId);
    }

    @GetMapping
    public Result getActivityList(  @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityList(page,size);
    }

    @AdminOnly
    @PutMapping("/status/{id}")
    public Result updateActivityStatus(@PathVariable Long id, @RequestParam Integer status){
        return activityService.updateActivityStatus(id,status);
    }

    //根据分类查询
    @GetMapping("/category")
    public Result getActivityListByCategory(@RequestParam String category,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByCategory(category,page,size);
    }

    //获取所有活动数
    @GetMapping("/count")
    public Result getActivityCount(){
        return activityService.getActivityCount();
    }

    //获取新发布活动数量
    @GetMapping("/new/count")
    public Result getNewActivityCount(){
        return activityService.getNewActivityCount();
    }

    @PostMapping("/signUp/{activityId}")
    public Result signUp(@PathVariable Long activityId){
        return activityService.signUp(activityId);
    }

    @PostMapping("/signDown/{activityId}")
    public Result signDown(@PathVariable Long activityId){
        return activityService.signDown(activityId);
    }

}
