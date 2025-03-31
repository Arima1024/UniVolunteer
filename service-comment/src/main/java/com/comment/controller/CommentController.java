package com.comment.controller;

import com.comment.domain.entity.Comment;
import com.comment.enums.SortTypeEnum;
import com.comment.service.CommentService;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController{

    private final CommentService commentService;

    /**
     * 用户评论查询接口，根据sortType参数决定排序方式：
     * - ascTime：按时间升序（默认）
     * - descTime：按时间降序
     * - highRating：高分在前
     * - lowRating：低分在前
     */
    @GetMapping("/user")
    public Result getUserComments(@RequestParam int page,
                                  @RequestParam int size,
                                  @RequestParam(defaultValue = "ascTime") String sortType) {
        Long userId = UserContext.getUserId();
        SortTypeEnum sortEnum = SortTypeEnum.fromString(sortType);

        return switch (sortEnum) {
            case DESC_TIME -> commentService.getCommentByUserIdWithDescTime(userId, page, size);
            case HIGH_RATING -> commentService.getCommentByUserIdWithHighRating(userId, page, size);
            case LOW_RATING -> commentService.getCommentByUserIdWithLowRating(userId, page, size);
            default -> commentService.getCommentByUserIdWithAscTime(userId, page, size);
        };
    }

    /**
     * 活动评论查询接口，根据sortType参数决定排序方式：
     * - ascTime：按时间升序（默认）
     * - descTime：按时间降序
     * - highRating：高分在前
     * - lowRating：低分在前
     */
    @GetMapping("/activity/{activityId}")
    public Result getActivityComments(@PathVariable Long activityId,
                                      @RequestParam int page,
                                      @RequestParam int size,
                                      @RequestParam(defaultValue = "ascTime") String sortType) {
        SortTypeEnum sortEnum = SortTypeEnum.fromString(sortType);

        return switch (sortEnum) {
            case DESC_TIME -> commentService.getCommentByActivityIdWithDescTime(activityId, page, size);
            case HIGH_RATING -> commentService.getCommentByActivityIdWithHighRatinig(activityId, page, size);
            case LOW_RATING -> commentService.getCommentByActivityIdWithLowRatinig(activityId, page, size);
            default -> commentService.getCommentByActivityIdWithAscTime(activityId, page, size);
        };
    }

    @GetMapping("/rating")
    public Result getAllRating(){
        return commentService.getAllRating();
    }

    @PostMapping("")
    public Result addComment(@RequestBody Comment comment) {
        return Result.ok(commentService.save(comment));
    }

    @PutMapping("")
    public Result updateComment(@RequestBody Comment comment) {
        return Result.ok(commentService.updateById(comment));
    }

    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Integer id) {
        return Result.ok(commentService.removeById(id));
    }
}
