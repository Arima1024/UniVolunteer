package com.univolunteer.activity.domain.dto;

import com.univolunteer.common.annotation.AdminOnly;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建活动时用的DTO
 */
@Data
@AllArgsConstructor
public class ActivityCreateDTO {

    private String title;
    private String description;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long maxVolunteers;
    private LocalDateTime signUpStartTime;
    private LocalDateTime signUpEndTime;
    private String fileUrl;
}
