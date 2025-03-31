package com.univolunteer.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.user.domain.entity.LoginStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginStatisticsMapper extends BaseMapper<LoginStatistics> {
}
