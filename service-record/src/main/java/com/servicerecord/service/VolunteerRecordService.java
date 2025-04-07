package com.servicerecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.servicerecord.domain.dto.RecordDTO;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.univolunteer.common.domain.dto.VolunteerDTO;
import com.univolunteer.common.result.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface VolunteerRecordService extends IService<VolunteerRecord> {

    Page<RecordDTO> getRecordsByTimeRange(int page, int size, LocalDateTime startTime, LocalDateTime finishTime);

    Page<RecordDTO> getRecordsByClassification(int page, int size, String classification, String sortType);

    Result addVolunteerRecord(Long activity);

    Result signIn(Long recordId, LocalDateTime signInTime);

    Result signOut(Long recordId, LocalDateTime signOutTime);

    Double calculateTotalTime(LocalDateTime startTime, LocalDateTime finishTime);

    VolunteerDTO getVolunteerTime(Long userId);
}
