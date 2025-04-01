package com.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.comment.domain.entity.Comment;
import com.univolunteer.common.result.Result;

public interface CommentService extends IService<Comment> {

    Result getCommentByUserIdWithDescTime(int page, int size);

    Result getCommentByUserIdWithHighRating(int page, int size);

    Result getCommentByUserIdWithAscTime(int page, int size);

    Result getCommentByUserIdWithLowRating(int page, int size);

    Result getCommentByActivityIdWithDescTime(Long activityId, int page, int size);

    Result getCommentByActivityIdWithHighRatinig(Long activityId, int page, int size);

    Result getCommentByActivityIdWithLowRatinig(Long activityId, int page, int size);

    Result getCommentByActivityIdWithAscTime(Long activityId, int page, int size);

    Result getAllRating();

    Result getUncommentedComments(int page, int size);

    Result getCommentedComments(int page, int size);
}
