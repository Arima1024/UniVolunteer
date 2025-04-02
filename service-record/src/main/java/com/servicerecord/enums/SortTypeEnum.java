package com.servicerecord.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortTypeEnum {
    ASC_TIME("ascTime"),
    DESC_TIME("descTime");

    private final String value;

    @JsonCreator
    public static SortTypeEnum fromString(@JsonProperty("sortType") String value) {
        return Arrays.stream(SortTypeEnum.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sortType: " + value));
    }


    SortTypeEnum(String value) {
        this.value = value;
    }
}
