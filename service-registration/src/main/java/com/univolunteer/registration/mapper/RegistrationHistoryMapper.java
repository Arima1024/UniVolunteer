package com.univolunteer.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.registration.domain.entity.RegistrationHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrationHistoryMapper extends BaseMapper<RegistrationHistory> {
}
