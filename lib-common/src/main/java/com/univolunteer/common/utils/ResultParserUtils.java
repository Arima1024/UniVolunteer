package com.univolunteer.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

public class ResultParserUtils {

    @Autowired
    private ObjectMapper objectMapper;  // 自动注入已配置的 ObjectMapper

    // 通用的解析方法，T 是目标类型
    public <T> T parseData(Object data, Class<T> targetType) {
        // 将 Result 中的 data 字段反序列化为目标类型
        return objectMapper.convertValue(data, targetType);
    }
}
