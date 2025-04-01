package com.comment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("comments")
public class Comment{
    private Long id; // 评论ID

    private Long activityId; // 活动ID

    private Long userId; // 评论者用户ID

    private Integer rating; // 评分（1-5星）

    private String content; // 评论内容

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 评论时间

    private Integer status;
}
