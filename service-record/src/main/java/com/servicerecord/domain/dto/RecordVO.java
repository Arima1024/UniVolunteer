package com.servicerecord.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecordVO {
    private Long recordId;
    private String userName;
    private String phone;
    private String school;
    private String activityName;
    private LocalDateTime startTime;
    private String activityLocation;
    private Double hours;
}
