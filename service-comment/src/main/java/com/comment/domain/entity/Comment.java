package com.comment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.comment.enums.CommentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName("comments")
public class Comment{
    private Long id; // 评论ID

    private Long activityId; // 活动ID

    private Long userId; // 评论者用户ID

    private Integer rating; // 评分（1-5星）

    private String content; // 评论内容

    private LocalDateTime createTime;

    private Integer status;

    public Comment(Long activityId, Long userId,int status) {
        this.activityId = activityId;
        this.userId = userId;
        this.status = status;
    }
}
