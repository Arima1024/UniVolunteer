package com.comment.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CommentStatus {

    UNCOMMENTED(0, "待评论"),
    COMMENTED(1, "已评论");

    @EnumValue
    private final int value;

    private final String description;

    CommentStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

}
