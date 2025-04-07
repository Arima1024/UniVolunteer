package com.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentRecordDTO {
    private Long recordId;
    private Double hours;
    private Integer completionStatus;
    private LocalDateTime signInTime;
    private LocalDateTime signOutTime;
}
