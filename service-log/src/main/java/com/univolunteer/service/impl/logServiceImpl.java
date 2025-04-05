package com.univolunteer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.api.client.UserClient;
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
    public Result getLog() {
        List<AuditLog> list = list();
        List<AuditLogDTO> auditLogDTOS=new ArrayList<>();
        if (list.isEmpty()) {
            return Result.fail("暂无记录");
        }
        for (AuditLog auditLog : list) {
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
        return Result.ok(auditLogDTOS);
    }
}
