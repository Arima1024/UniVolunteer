package com.servicerecord.domain.dto;

import java.time.LocalDateTime;

public class RecordVO {
    private Long recordId;
    private String userName;
    private String activityName;
    private String activityLocation;
    private Double hours;
    private Integer completionStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
}
