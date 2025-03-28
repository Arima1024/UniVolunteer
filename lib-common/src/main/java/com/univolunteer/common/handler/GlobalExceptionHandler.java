package com.univolunteer.common.handler;

import com.univolunteer.common.exception.AdminException;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.utils.DuplicateKeyMessageParser;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// 全局异常处理
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理权限异常
    @ExceptionHandler(AdminException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleAccessDenied(AdminException ex) {
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        return Result.fail("无权限: " + ex.getMessage());
    }

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleAll(Exception ex) {
        ex.printStackTrace(); // 方便调试
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        return Result.fail("服务器错误: " + ex.getMessage());
    }

    // 处理重复键异常

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        String msg = DuplicateKeyMessageParser.parseMessage(ex.getMessage());
        return Result.fail(msg);
    }
}
