package com.univolunteer.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.common.domain.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    @Select("SELECT DISTINCT category FROM activities WHERE category IS NOT NULL")
    List<String> selectDistinctCategories();
}
