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
        queryWrapper.eq(Feedback::getStatus,FeedbackStatus.NEW.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    @Override
    public Result getFeedbackByAcceptedStatus(int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        queryWrapper.eq(Feedback::getStatus,FeedbackStatus.ACCEPTED.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    @Override
    public Result getFeedbackByResolvedStatus(int page, int size) {

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<Feedback>();
        queryWrapper.eq(Feedback::getStatus,FeedbackStatus.RESOLVED.getValue());
        Page<Feedback> feedbackPage = new Page<>(page, size);
        return Result.ok(this.baseMapper.selectPage(feedbackPage, queryWrapper));

    }

    public Result addFeedback(Feedback feedback) {
        // 校验反馈内容不能为空
        if (feedback.getContent() == null || feedback.getContent().trim().isEmpty()) {
            return Result.fail("反馈内容不能为空");
        }

        // 设置默认状态为 "新提交"
        feedback.setStatus(FeedbackStatus.NEW);
        boolean success =this.save(feedback);  // 调用 MyBatis-Plus 保存数据
        return success ? Result.ok("反馈提交成功") : Result.fail("提交失败，请稍后再试");
    }


}

