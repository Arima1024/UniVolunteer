package com.univolunteer.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuplicateKeyMessageParser {

    // 匹配格式：Duplicate entry '1234567890' for key 'phone'
    private static final Pattern DUPLICATE_ENTRY_PATTERN = Pattern.compile("Duplicate entry '(.+?)' for key '(.+?)'");

    public static String parseMessage(String exceptionMessage) {
        if (exceptionMessage == null || exceptionMessage.isBlank()) {
            return "字段值已存在";
        }

        Matcher matcher = DUPLICATE_ENTRY_PATTERN.matcher(exceptionMessage);
        if (matcher.find()) {
            String value = matcher.group(1);     // 提取字段值
            String key = matcher.group(2);       // 提取字段名（假设索引名和字段名一致）
            return String.format("%s: %s 已经存在", key, value).replaceFirst("^\\w+\\.", "");
        }

        return "字段值已存在";
    }
}
