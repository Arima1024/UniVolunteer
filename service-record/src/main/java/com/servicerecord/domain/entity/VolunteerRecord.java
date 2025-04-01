package com.servicerecord.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("volunteer_records")
public class VolunteerRecord {
    private Long id;
    private Long userId;
    private Long activityId;
    private Double hours;
    private Integer completionStatus;
    private LocalDateTime confirmTime;
    private LocalDateTime outTime;
    private String organizerFeedback;
}