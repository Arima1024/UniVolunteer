package com.comment.domain.dto;

import lombok.Data;

@Data
public class RatingDTO {
    private Integer rating;
    private Long count;  // 这里可以用 Long 以防 COUNT(*) 类型不匹配

    // 记得加 getter 和 setter
}
