package com.comment.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SortTypeEnum {
    ASC_TIME("ascTime"),
    DESC_TIME("descTime"),
    HIGH_RATING("highRating"),
    LOW_RATING("lowRating");

    private final String value;

    @JsonCreator
    public static SortTypeEnum fromString(@JsonProperty("sortType") String value) {
        for (SortTypeEnum type : SortTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid sortType: " + value);
    }

    SortTypeEnum(String value) {
        this.value = value;
    }
}
