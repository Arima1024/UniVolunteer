package com.univolunteer.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.common.domain.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}
