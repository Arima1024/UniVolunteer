package com.comment.controller;

import com.comment.domain.entity.Feedback;
import com.comment.service.FeedbackService;
import com.univolunteer.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 根据用户ID获取反馈
     */
    @GetMapping("/user")
    public Result getFeedbackByUser(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return feedbackService.getFeedbackByUserId(page, size);
    }

    /**
     * 获取所有新提交的反馈
     */
    @GetMapping("/status/new")
    public Result getNewFeedback(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return feedbackService.getFeedbackByNewStatus(page, size);
    }

    /**
     * 获取已接受的反馈
     */
    @GetMapping("/status/accepted")
    public Result getAcceptedFeedback(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return feedbackService.getFeedbackByAcceptedStatus(page, size);
    }

    /**
     * 获取已解决的反馈
     */
    @GetMapping("/status/resolved")
    public Result getResolvedFeedback(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return feedbackService.getFeedbackByResolvedStatus(page, size);
    }

    @PostMapping("/add")
    public Result addFeedback(@RequestBody Feedback feedback) {

        return feedbackService.addFeedback(feedback);

    }

}