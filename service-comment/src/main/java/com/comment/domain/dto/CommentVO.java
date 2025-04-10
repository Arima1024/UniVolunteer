package com.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentVO{
    private Long id;
    private String userName;
    private LocalDateTime createTime;
    private Integer rating;
    private String content;
}
