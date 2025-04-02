package com.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecordDTO {
    private Long id;
    private Long userId;
    private Long activityId;
    private Double hours;
    private Integer completionStatus;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
    private String organizerFeedback;
}
