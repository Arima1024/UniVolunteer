package com.comment.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum FeedbackStatus {

    NEW(0, "新"),
    ACCEPTED(1, "已评论"),
    RESOLVED(2,"2");

    @EnumValue
    private final int value;

    private final String description;

    FeedbackStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

}
