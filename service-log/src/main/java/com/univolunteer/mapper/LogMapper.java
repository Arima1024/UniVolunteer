package com.univolunteer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.common.domain.entity.AuditLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<AuditLog> {
}
