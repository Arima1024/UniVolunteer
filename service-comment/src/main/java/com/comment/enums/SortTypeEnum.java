package com.comment.enums;

import java.util.HashMap;
import java.util.Map;

public enum SortTypeEnum {
    ASC_TIME("ascTime"),
    DESC_TIME("descTime"),
    HIGH_RATING("highRating"),
    LOW_RATING("lowRating");

    private final String value;
    private static final Map<String, SortTypeEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (SortTypeEnum type : SortTypeEnum.values()) {
            LOOKUP_MAP.put(type.value, type);
        }
    }

    SortTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortTypeEnum fromString(String str) {
        return LOOKUP_MAP.getOrDefault(str, ASC_TIME); // 默认按时间升序排序
    }
}
