package com.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.comment.domain.dto.RatingDTO;
import com.comment.domain.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT rating, COUNT(*) AS count FROM comments WHERE rating BETWEEN 1 AND 5 GROUP BY rating")
    List<RatingDTO> countRatings();

}


