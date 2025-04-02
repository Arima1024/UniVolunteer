package com.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.comment.domain.entity.Comment;
import com.univolunteer.common.result.Result;

public interface CommentService extends IService<Comment> {

    Result getCommentByUserIdWithDescTime(Long userId, int page, int size);

    Result getCommentByUserIdWithHighRating(Long userId, int page, int size);

    Result getCommentByUserIdWithAscTime(Long userId, int page, int size);

    Result getCommentByUserIdWithLowRating(Long userId, int page, int size);

    Result getCommentByActivityIdWithDescTime(Long activityId, int page, int size);

    Result getCommentByActivityIdWithHighRatinig(Long activityId, int page, int size);

    Result getCommentByActivityIdWithLowRatinig(Long activityId, int page, int size);

    Result getCommentByActivityIdWithAscTime(Long activityId, int page, int size);


    Result getAllRating();
}
