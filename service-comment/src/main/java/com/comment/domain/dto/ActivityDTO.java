package com.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityDTO {

    private Long id;
    private String title;
    private String description;

}
