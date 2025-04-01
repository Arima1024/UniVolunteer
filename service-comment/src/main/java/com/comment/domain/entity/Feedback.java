package com.comment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.comment.enums.FeedbackStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("feedback")
public class Feedback{
    private Long id; // 反馈ID

    private Long userId; // 反馈人用户ID，可为NULL表示匿名

    private String title; // 反馈标题

    private String content; // 反馈内容

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 创建时间

    private FeedbackStatus status; // 处理状态（0=新提交，1=已受理，2=已解决）

    private Integer handlerId; // 处理人管理员ID
}
