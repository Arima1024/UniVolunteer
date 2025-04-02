package com.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.comment.domain.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
}
