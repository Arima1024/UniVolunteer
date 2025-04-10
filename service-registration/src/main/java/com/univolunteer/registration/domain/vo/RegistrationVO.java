package com.univolunteer.registration.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationVO {
    @TableId
    private Long id;  // 报名ID

    private Long userId;  // 报名人ID（外键）

    private LocalDateTime applyTime;  // 报名时间

    private Integer status = 0;  // 报名状态（0=申请，1=通过，2=拒绝，3=取消）

    private Long activityId;

    private String title; // 活动标题

    private Long maxVolunteers;

    private Long currentSignUpCount;

    private String imgUrl;

    private String category; // 活动类别

    private String location; // 活动地点


    private LocalDateTime startTime; // 开始时间

    private LocalDateTime endTime; // 结束时间

    private LocalDateTime signUpStartTime;

    private LocalDateTime signUpEndTime;

    private String username;

    private String phone;

    private String email;

    private Double hours;

    private Long count;

    private String organizationName;
}
