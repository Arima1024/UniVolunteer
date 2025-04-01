package com.servicerecord.controller;

import com.servicerecord.domain.entity.VolunteerRecord;
import com.servicerecord.service.VolunteerRecordService;
import com.univolunteer.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteerRecord")
@RequiredArgsConstructor
public class VolunteerRecordController {

    private final VolunteerRecordService volunteerRecordService;

    //负责待开展、已结束的活动的列表，同时调用activity里面表的状态返回给前端，查找所有已完成的活动
    //签退时间一旦到达，自动视为放弃
    //签到改为开展状态，签退改为已结束，两者相减则为时间（判断签到时间与签退时间的规范）

    @GetMapping()
    public Result volunteerRecord() {
        ;
    }

    @PutMapping
    public Result update(@RequestBody VolunteerRecord volunteerRecord) {
        return Result.ok(volunteerRecordService.updateById(volunteerRecord));
    }

}
