package com.univolunteer.common.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityVO {

    private Long id; // 活动ID

    private String title; // 活动标题

    private String description; // 活动详细描述


    private Long userId; // 发布人用户ID（外键）

    private String category; // 活动类别

    private String location; // 活动地点


    private LocalDateTime startTime; // 开始时间

    private LocalDateTime endTime; // 结束时间


    private Long maxVolunteers; // 最大志愿者数

    private Integer status; // 活动状态（0=草稿，1=审核中，2=招募中，3=已结束）


    private LocalDateTime createTime; // 创建时间


    private Long currentSignUpCount; // 当前报名人数

    // 活动报名时间

    private LocalDateTime signUpStartTime;

    private LocalDateTime signUpEndTime;

    private String imgUrl;

    private String username;

    private String phone;

    private String email;

    private String reason;
}
