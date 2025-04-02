package com.servicerecord.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.servicerecord.service.VolunteerRecordService;
import com.univolunteer.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/volunteerRecord")
@RequiredArgsConstructor
public class VolunteerRecordController {

    private final VolunteerRecordService volunteerRecordService;

    //提供签到时间

    @PutMapping("/signIn")
    public Result signIn(@RequestParam Long activityId,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime signInTime) {
        return volunteerRecordService.signIn(activityId,signInTime);
    }

    /**
     * 提供签退时间，并自动计算服务时长
     * @param activityId
     * @param signOutTime
     * @return
     */

    @PutMapping("signOut")
    public Result signOut(@RequestParam Long activityId,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime signOutTime) {
        return volunteerRecordService.signOut(activityId,signOutTime);
    }

    /**
     * 当活动报名成功时自动添加报名记录
     * @param activityId
     * @return
     */

    @PostMapping("/add")
    public Result addVolunteerRecord(@RequestParam Long activityId) {
        return volunteerRecordService.addVolunteerRecord(activityId);
    }

    /**
     * 根据时间范围查询志愿记录
     */
    @GetMapping("/{startTime}&{finishTime}")
    public Result getRecordsByTime(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime finishTime) {
        return Result.ok(volunteerRecordService.getRecordsByTimeRange(page,size,startTime, finishTime));
    }

    /**
     * 计算用户服务总时长
     */
    @GetMapping("userTotal")
    public Result getUserTotalTime(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime finishTime) {
        return Result.ok(volunteerRecordService.calculateTotalTime(startTime,finishTime));
    }


    /**
     * 按分类和排序方式获取志愿记录
     */
    @GetMapping("/all")
    public Result getAllRecords(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam String classification,
                                @RequestParam(defaultValue = "ascTime") String sortType) {
        return Result.ok(volunteerRecordService.getRecordsByClassification(page,size,classification, sortType));
    }


}
