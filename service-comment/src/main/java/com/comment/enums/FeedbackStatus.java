package com.comment.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum FeedbackStatus {

    NEW(0, "新提交"),
    ACCEPTED(1, "已受理"),
    RESOLVED(2, "已解决");

    @EnumValue
    private final int value;

    private final String description;

    FeedbackStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
