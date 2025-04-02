package com.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.comment.domain.entity.Feedback;
import com.univolunteer.common.result.Result;

public interface FeedbackService extends IService<Feedback> {
    Result getFeedbackByUserId(Long userId, int page, int size);

    Result getFeedbackByNewStatus(int page, int size);

    Result getFeedbackByAcceptedStatus(int page, int size);

    Result getFeedbackByResolvedStatus(int page, int size);

    Result addFeedback(Feedback feedback);
}
