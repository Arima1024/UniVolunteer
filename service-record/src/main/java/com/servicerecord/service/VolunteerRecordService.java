package com.servicerecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.univolunteer.common.result.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface VolunteerRecordService extends IService<VolunteerRecord> {

    Page<VolunteerRecord> getRecordsByTimeRange(int page, int size, LocalDateTime startTime, LocalDateTime finishTime);

    Page<VolunteerRecord> getRecordsByClassification(int page,int size,String classification, String sortType);

    Result addVolunteerRecord(Long activity);

    Result signIn(Long activityId, LocalDateTime signInTime);

    Result signOut(Long activityId, LocalDateTime signOutTime);

    Double calculateTotalTime(LocalDateTime startTime, LocalDateTime finishTime);

    Result getTotalNums();
}
