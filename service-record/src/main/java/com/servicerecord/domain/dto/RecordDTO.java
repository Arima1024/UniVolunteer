package com.servicerecord.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecordDTO {
    private Long recordId;
    private String activityName;
    private String activityLocation;
    private Double hours;
    private Integer completionStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
}
