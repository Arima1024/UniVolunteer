package com.comment.domain.dto;

import com.comment.domain.entity.Comment;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private String activityName;
    private Integer rating;
    private String content;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
    private Double hours;

}
