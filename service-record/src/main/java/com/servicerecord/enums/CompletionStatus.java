package com.servicerecord.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CompletionStatus {

    NOT_STARTED(0, "未开展"),
    IN_PROGRESS(1, "开展中"),
    COMPLETED(2, "已完成"),
    ABANDONED(3, "已放弃");

    @EnumValue
    private final int value;
    private final String description;

    CompletionStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
