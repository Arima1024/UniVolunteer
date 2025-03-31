package com.comment.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.comment.domain.entity.Feedback;
import com.comment.enums.FeedbackStatus;
import com.comment.mapper.FeedbackMapper;
import com.comment.service.FeedbackService;
import com.univolunteer.common.result.Result;
import org.springframework.stereotype.Service;

import static com.comment.enums.FeedbackStatus.NEW;
import static com.comment.enums.FeedbackStatus.ACCEPTED;
import static com.comment.enums.FeedbackStatus.RESOLVED;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public Result getFeedbackByUserId(Long userId, int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        queryWrapper.eq(Feedback::getUserId, userId);
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    @Override
    public Result getFeedbackByNewStatus(int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        System.out.println("Querying for status: " + FeedbackStatus.NEW.getValue());
        queryWrapper.eq(Feedback::getStatus,NEW.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    @Override
    public Result getFeedbackByAcceptedStatus(int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        System.out.println("Querying for status: " + FeedbackStatus.ACCEPTED.getValue());
        queryWrapper.eq(Feedback::getStatus,ACCEPTED.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    @Override
    public Result getFeedbackByResolvedStatus(int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        queryWrapper.eq(Feedback::getStatus,RESOLVED.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }
}

