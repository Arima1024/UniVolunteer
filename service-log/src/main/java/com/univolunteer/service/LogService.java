package com.univolunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.domain.entity.AuditLog;

public interface LogService extends IService<AuditLog> {
    Result addLog(AuditLog auditLog);

    Result getLog(int page, int size);
}
