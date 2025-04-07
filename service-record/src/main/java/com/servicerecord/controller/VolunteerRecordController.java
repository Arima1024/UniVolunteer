package com.servicerecord.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.servicerecord.service.VolunteerRecordService;
import com.univolunteer.common.annotation.AdminOnly;
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
    public Result signIn(@RequestParam Long recordId) {
        return volunteerRecordService.signIn(recordId);
    }

    /**
     * 提供签退时间，并自动计算服务时长
     * @param recordId
     * @return
     */


    @PutMapping("/signOut")
    public Result signOut(@RequestParam Long recordId) {
        return volunteerRecordService.signOut(recordId);
    }



    @PostMapping("/add")
    public Result addVolunteerRecord(@RequestParam Long activityId,@RequestParam Long userId) {
        return volunteerRecordService.addVolunteerRecord(activityId,userId);
    }

    /**
     * 根据时间范围查询志愿记录
     */
    @GetMapping("/{startTime}&{finishTime}")
    public Result getRecordsByTime(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss") LocalDateTime startTime,
                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss") LocalDateTime finishTime) {
        return Result.ok(volunteerRecordService.getRecordsByTimeRange(page,size,startTime, finishTime));
    }

    @GetMapping("/volunteerRecord/{activityId}/{userId}")
    public Result getRecord(@PathVariable("activityId") Long activityId, @PathVariable("userId") Long userId){
        return Result.ok(volunteerRecordService.getRecord(activityId,userId));
    }

    /**
     * 计算用户服务总时长
     */

    @GetMapping("/userTotal")

    public Result getUserTotalTime(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss") LocalDateTime startTime,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss") LocalDateTime finishTime) {
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


    @AdminOnly
    @PutMapping()
    public Result updateVolunteerRecord(@RequestBody VolunteerRecord volunteerRecord) {
        return Result.ok(volunteerRecord);
    }

    @GetMapping("/user")
    public Result getVolunteerTime(@RequestParam Long userId) {
        return Result.ok(volunteerRecordService.getVolunteerTime(userId));
    }

    @AdminOnly
    @GetMapping("/admin/all")
    public Result getAllOnlyAdmin(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String school,
                                  @RequestParam(required = false) String activityName,
                                  @RequestParam(required = false) String activityLocation,
                                  @RequestParam(required = false) LocalDateTime startTime) {
        return Result.ok(volunteerRecordService.getAllOnlyAdmin(page,size,name,school,activityName,activityLocation,startTime));

    }

}
