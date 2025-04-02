package com.univolunteer.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.registration.domain.entity.Registration;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrationMapper extends BaseMapper<Registration> {
}
