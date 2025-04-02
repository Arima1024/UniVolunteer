package com.univolunteer.common.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("activities") // 对应表名
public class Activity {

    @TableId(type = IdType.AUTO) // 自增ID
    private Long id; // 活动ID

    private String title; // 活动标题

    private String description; // 活动详细描述

    @TableField("user_id")
    private Long userId; // 发布人用户ID（外键）

    private String category; // 活动类别

    private String location; // 活动地点

    @TableField("start_time")

    private LocalDateTime startTime; // 开始时间

    @TableField("end_time")

    private LocalDateTime endTime; // 结束时间

    @TableField("max_volunteers")
    private Long maxVolunteers; // 最大志愿者数

    private Integer status; // 活动状态（0=草稿，1=审核中，2=招募中，3=已结束）

    @TableField("create_time")

    private LocalDateTime createTime; // 创建时间

    @TableField("currentSignUpCount")
    private Long currentSignUpCount; // 当前报名人数

    // 活动报名时间

    private LocalDateTime signUpStartTime;

    private LocalDateTime signUpEndTime;

    private LocalDateTime auditTime;

    private String reason;


}
