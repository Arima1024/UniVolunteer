package com.comment.controller;

import com.comment.domain.dto.ActivityDTO;
import com.comment.domain.dto.CommentRecordDTO;
import com.comment.domain.dto.CommentResponseDTO;
import com.comment.domain.entity.Comment;
import com.comment.enums.SortTypeEnum;
import com.comment.service.CommentService;
import com.univolunteer.api.client.ActivityClient;
import com.univolunteer.api.client.RecordClient;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.utils.ResultParserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController{

    private final CommentService commentService;

    private final ActivityClient activityClient;

    private final RecordClient recordClient;

    private final ResultParserUtils resultParserUtils;

    @PutMapping("/toComment")
    public Result updateComment(@RequestBody Comment comment){

        comment.setCreateTime(LocalDateTime.now());
        comment.setStatus(1);
        commentService.updateById(comment);
        return Result.ok();

    }

    //存在待评论和已评论两个状态，需要通过调用activity来返回活动信息。调用record来返回活动记录（签到时间和签退时间）
    @GetMapping("/uncommented")
    @Transactional
    public Result getUncommentedComments() {

        // 获取待评论的评论列表
        List<Comment> uncommentedComments = commentService.getUncommentedComments();

        // 遍历评论，获取活动信息和签到记录
        List<CommentResponseDTO> response = uncommentedComments.stream().map(comment -> {
            // 远程调用 service-activity 获取活动信息
            Result activityResult = activityClient.getActivity(comment.getActivityId());
            ActivityDTO activity = resultParserUtils.parseData(activityResult.getData(), ActivityDTO.class);

            // 远程调用 service-record 获取签到记录
            Result recordResult = recordClient.getRecord(comment.getActivityId(), comment.getUserId());
            CommentRecordDTO record = resultParserUtils.parseData(recordResult.getData(), CommentRecordDTO.class);

            // 封装返回 DTO
            return new CommentResponseDTO(comment.getId(), activity.getTitle(), comment.getRating(), comment.getContent(), record.getSignInTime(), record.getSignOutTime(), record.getHours());
        }).collect(Collectors.toList());

        return Result.ok(response);

    }

    @GetMapping("/commented")
    @Transactional
    public Result getCommentedComments() {

        // 获取待评论的评论列表
        List<Comment> commentedComments = commentService.getCommentedComments();

        // 遍历评论，获取活动信息和签到记录
        List<CommentResponseDTO> response = commentedComments.stream().map(comment -> {
            // 远程调用 service-activity 获取活动信息
            Result activityResult = activityClient.getActivity(comment.getActivityId());
            ActivityDTO activity = resultParserUtils.parseData(activityResult.getData(), ActivityDTO.class);

            // 远程调用 service-record 获取签到记录
            Result recordResult = recordClient.getRecord(comment.getActivityId(), comment.getUserId());
            CommentRecordDTO record = resultParserUtils.parseData(recordResult.getData(), CommentRecordDTO.class);

            // 封装返回 DTO
            return new CommentResponseDTO(comment.getId(), activity.getTitle(), comment.getRating(), comment.getContent(), record.getSignInTime(), record.getSignOutTime(), record.getHours());
        }).collect(Collectors.toList());

        return Result.ok(response);

    }

    /**
     * 用户评论查询接口，根据sortType参数决定排序方式：
     * - ascTime：按时间升序（默认）
     * - descTime：按时间降序
     * - highRating：高分在前
     * - lowRating：低分在前
     */
    @GetMapping("/user")
    public Result getUserComments(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "ascTime") String sortType) {

        SortTypeEnum sortEnum = SortTypeEnum.fromString(sortType);

        return switch (sortEnum) {
            case DESC_TIME -> commentService.getCommentByUserIdWithDescTime(page, size);
            case HIGH_RATING -> commentService.getCommentByUserIdWithHighRating(page, size);
            case LOW_RATING -> commentService.getCommentByUserIdWithLowRating(page, size);
            default -> commentService.getCommentByUserIdWithAscTime(page, size);
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
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size,
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


    @PostMapping("/auto-generate/{activityId}/{userId}")

    public Result autoGenerateComments(@PathVariable Long activityId,@PathVariable Long userId) {
        commentService.autoGenerateComments(activityId,userId);
        return Result.ok();
    }
}
