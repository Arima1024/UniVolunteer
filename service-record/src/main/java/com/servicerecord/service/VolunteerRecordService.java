package com.servicerecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.servicerecord.domain.dto.CommentRecordDTO;
import com.servicerecord.domain.dto.RecordDTO;
import com.servicerecord.domain.dto.RecordVO;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.univolunteer.common.domain.dto.VolunteerDTO;
import com.univolunteer.common.result.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface VolunteerRecordService extends IService<VolunteerRecord> {

    Page<RecordDTO> getRecordsByTimeRange(int page, int size, LocalDateTime startTime, LocalDateTime finishTime);

    Page<RecordDTO> getRecordsByClassification(int page, int size, String classification, String sortType);

    Result addVolunteerRecord(Long activity,Long userId);

    Result signIn(Long recordId);

    Result signOut(Long recordId);

    Double calculateTotalTime(LocalDateTime startTime, LocalDateTime finishTime);

    VolunteerDTO getVolunteerTime(Long userId);

    Page<RecordVO> getAllOnlyAdmin(int page, int size, String name, String school, String activityName, String activityLocation, LocalDateTime startTime);

    CommentRecordDTO getRecord(Long activityId, Long userId);
}
