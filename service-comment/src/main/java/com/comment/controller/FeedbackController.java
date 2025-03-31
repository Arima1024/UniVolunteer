package com.comment.controller;

import com.comment.domain.entity.Feedback;
import com.comment.service.FeedbackService;
import com.univolunteer.common.annotation.AdminOnly;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/new")
    public Result getFeedbackByNewStatus(@RequestParam(defaultValue = "1")int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return feedbackService.getFeedbackByNewStatus(page,size);
    }

    @GetMapping("/accepted")
    public Result getFeedbackByAcceptedStatus(@RequestParam(defaultValue = "1")int page,
                                              @RequestParam(defaultValue = "10")int size){
        return feedbackService.getFeedbackByAcceptedStatus(page,size);
    }

    @GetMapping("/resolved")
    public Result getFeedbackByResolvedStatus(@RequestParam(defaultValue = "1")int page,
                                              @RequestParam(defaultValue = "10") int size){
        return feedbackService.getFeedbackByResolvedStatus(page,size);
    }

    @GetMapping("/user")
    public Result getFeedbackByUser(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Long userId= UserContext.getUserId();
        return feedbackService.getFeedbackByUserId(userId,page,size);
    }

    @PostMapping("")
    public Result addFeedback(@RequestBody Feedback feedback) {
        return Result.ok(feedbackService.save(feedback));
    }

    @PutMapping("")
    public Result updateFeedback(@RequestBody Feedback feedback) {
        return Result.ok(feedbackService.updateById(feedback));
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public Result deleteFeedback(@PathVariable Long id) {
        return Result.ok(feedbackService.removeById(id));
    }
}
