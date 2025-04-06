package com.univolunteer.activity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univolunteer.activity.domain.dto.StatusDTO;
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

    //判断是否能报名
    @GetMapping("/check/{activityId}")
    public Result check(@PathVariable Long activityId){
        return activityService.check(activityId);
    }

    @GetMapping
    public Result getActivityList(  @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityList(page,size);
    }

    @AdminOnly
    @PutMapping("/status/{id}")
    public Result updateActivityStatus(@PathVariable Long id, @RequestBody StatusDTO statusDTO){
        return activityService.updateActivityStatus(id,statusDTO.getStatus(),statusDTO.getReason());
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

    //根据地点返回活动列表
    @GetMapping("/location")
    public Result getActivityListByLocation(@RequestParam String location,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByLocation(location,page,size);
    }

    //根据时间天返回活动列表
    @GetMapping("/time")
    public Result getActivityListByTime(@RequestParam String time,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByTime(time,page,size);
    }
    //根据状态 待审核 审核通过 已结束  审核不通过 返回活动列表

    @GetMapping("/byTimeStatus")

    public Result getActivityListByStatus(@RequestParam Integer status,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByStatus(status,page,size);
    }

    @GetMapping("/search")
    public Result searchActivity(@RequestParam String keyword,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size){

        return activityService.searchActivity(keyword,page,size);
    }

    @GetMapping("/admin/time")
    public Result getActivityListByTimeAdmin(@RequestParam Integer status,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByTimeAdmin(status,page,size);
    }

    @GetMapping("/admin/status")
    public Result getActivityListByStatusAdmin(@RequestParam Integer status,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByStatusAdmin(status,page,size);
    }

    @GetMapping("/admin/allStatus")
    public Result getActivityListByAllStatus(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(required = false) Integer timeStatus,
                                             @RequestParam(required = false) String category,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size){
        return activityService.getActivityListByAllStatus(keyword,status,timeStatus,category,page,size);
    }


    @GetMapping("/allCategory")
    public Result getAllCategory(){
        return activityService.getAllCategory();
    }

    @GetMapping("/countByUserId")
    public Result getActivityCountByUserId(@RequestParam Long userId){
        return activityService.getActivityCountByUserId(userId);
    }

}
