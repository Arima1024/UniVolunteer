package com.univolunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.api.client.UserClient;
import com.univolunteer.common.domain.entity.Activity;
import com.univolunteer.common.domain.vo.ActivityVO;
import com.univolunteer.common.domain.vo.UserNotificationVO;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.domain.entity.AuditLog;
import com.univolunteer.common.utils.ResultParserUtils;
import com.univolunteer.domain.AuditLogDTO;
import com.univolunteer.mapper.LogMapper;
import com.univolunteer.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class logServiceImpl extends ServiceImpl<LogMapper, AuditLog> implements LogService {

    private final LogMapper logMapper;

    private final UserClient userClient;

    private final ResultParserUtils resultParserUtils;

    @Override
    public Result addLog(AuditLog auditLog) {
        save(auditLog);
        return null;
    }

    @Override
    public Result getLog(int page, int size) {
        Page<AuditLog> logPage = new Page<>(page, size);
        QueryWrapper<AuditLog> queryWrapper = new QueryWrapper<>();
        Page<AuditLog> logs = this.page(logPage, queryWrapper);
        IPage<AuditLogDTO> auditLogDTOIPage = getAllLog(page, size, logs);
        return Result.ok(auditLogDTOIPage.getRecords(), auditLogDTOIPage.getTotal());
    }

    private IPage<AuditLogDTO> getAllLog(int page, int size, Page<AuditLog> logs) {
        List<AuditLog> auditLogList = logs.getRecords();
        List<AuditLogDTO> auditLogDTOS = new ArrayList<>();
        for (AuditLog auditLog : auditLogList) {
            AuditLogDTO auditLogDTO = new AuditLogDTO();
            Long userId = auditLog.getUserId();
            Result result = userClient.getUser(userId);
            if (!result.getSuccess()) {
                continue;
            }
            UserNotificationVO userNotificationVO = resultParserUtils.parseData(result.getData(), UserNotificationVO.class);
            BeanUtils.copyProperties(auditLog, auditLogDTO);
            auditLogDTO.setUserName(userNotificationVO.getUsername());
            auditLogDTO.setPhone(userNotificationVO.getPhone());
            auditLogDTOS.add(auditLogDTO);
        }
        Page<AuditLogDTO> auditLogDTOPage = new Page<>();
        auditLogDTOPage.setRecords(auditLogDTOS);
        auditLogDTOPage.setTotal(logs.getTotal());
        auditLogDTOPage.setPages(logs.getPages());
        auditLogDTOPage.setCurrent(logs.getCurrent());
        return auditLogDTOPage;
    }
}
