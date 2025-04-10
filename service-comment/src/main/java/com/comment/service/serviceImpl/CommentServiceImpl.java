package com.comment.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.comment.domain.dto.CommentVO;
import com.comment.domain.entity.Comment;
import com.comment.mapper.CommentMapper;
import com.comment.service.CommentService;
import com.univolunteer.api.client.UserClient;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.vo.UserNotificationVO;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.utils.ResultParserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "comments")
@RequiredArgsConstructor()
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentMapper commentMapper;

    private final UserClient userClient;

    private final ResultParserUtils resultParserUtils;
    @Override
    public Result getCommentByUserIdWithDescTime(int page, int size) {
        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId, userId);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByUserIdWithHighRating(int page, int size) {
        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId, userId).ge(Comment::getRating,3);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByUserIdWithAscTime(int page, int size) {
        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId, userId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByUserIdWithLowRating(int page, int size) {
        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId, userId).lt(Comment::getRating,3);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByActivityIdWithDescTime(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).eq(Comment::getStatus,1);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(page, size), queryWrapper);
        long total = commentPage.getTotal();

        List<CommentVO> commentVOList=commentPage.getRecords().stream().map(comment -> {
            UserNotificationVO user = resultParserUtils.parseData(userClient.getUser(comment.getUserId()).getData(), UserNotificationVO.class);
            String username = user.getUsername();
            return new CommentVO(comment.getId(),username,comment.getCreateTime(),comment.getRating(),comment.getContent());
        }).collect(Collectors.toList());

        Page<CommentVO> commentVOPage = new Page<>(page, size, total);
        commentVOPage.setRecords(commentVOList);
        return Result.ok(commentVOPage);
    }

    @Override
    public Result getCommentByActivityIdWithHighRating(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).ge(Comment::getRating,3).eq(Comment::getStatus,1);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(page, size), queryWrapper);
        long total = commentPage.getTotal();

        List<CommentVO> commentVOList=commentPage.getRecords().stream().map(comment -> {
            UserNotificationVO user = resultParserUtils.parseData(userClient.getUser(comment.getUserId()).getData(), UserNotificationVO.class);
            String username = user.getUsername();
            return new CommentVO(comment.getId(),username,comment.getCreateTime(),comment.getRating(),comment.getContent());
        }).collect(Collectors.toList());

        Page<CommentVO> commentVOPage = new Page<>(page, size, total);
        commentVOPage.setRecords(commentVOList);
        return Result.ok(commentVOPage);
    }

    @Override
    public Result getCommentByActivityIdWithLowRating(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).lt(Comment::getRating,3).eq(Comment::getStatus,1);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(page, size), queryWrapper);
        long total = commentPage.getTotal();

        List<CommentVO> commentVOList=commentPage.getRecords().stream().map(comment -> {
            UserNotificationVO user = resultParserUtils.parseData(userClient.getUser(comment.getUserId()).getData(), UserNotificationVO.class);
            String username = user.getUsername();
            return new CommentVO(comment.getId(),username,comment.getCreateTime(),comment.getRating(),comment.getContent());
        }).collect(Collectors.toList());

        Page<CommentVO> commentVOPage = new Page<>(page, size, total);
        commentVOPage.setRecords(commentVOList);
        return Result.ok(commentVOPage);
    }

    @Override
    public Result getCommentByActivityIdWithAscTime(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).eq(Comment::getStatus,1);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(page, size), queryWrapper);
        long total = commentPage.getTotal();

        List<CommentVO> commentVOList=commentPage.getRecords().stream().map(comment -> {
            UserNotificationVO user = resultParserUtils.parseData(userClient.getUser(comment.getUserId()).getData(), UserNotificationVO.class);
            String username = user.getUsername();
            return new CommentVO(comment.getId(),username,comment.getCreateTime(),comment.getRating(),comment.getContent());
        }).collect(Collectors.toList());

        Page<CommentVO> commentVOPage = new Page<>(page, size, total);
        commentVOPage.setRecords(commentVOList);
        return Result.ok(commentVOPage.getRecords(),commentVOPage.getTotal());
    }

    @Override
    public Result getAllRating() {

        return Result.ok(commentMapper.countRatings());
    }

    @Override
    public List<Comment> getUncommentedComments() {

        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId,userId).eq(Comment::getStatus,0);
        return commentMapper.selectList(queryWrapper);

    }

    @Override
    public List<Comment> getCommentedComments() {

        Long userId= UserContext.getUserId();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId,userId).eq(Comment::getStatus,1);
        return commentMapper.selectList(queryWrapper);

    }

    @Override
    public void autoGenerateComments(Long activityId,Long userId) {

        this.save(new Comment(activityId,userId,0));

    }

    @Override
    public Result toComment(Long commentId,int rating,String content){
        Comment comment=this.getById(commentId);
        comment.setActivityId(comment.getActivityId());
        comment.setUserId(comment.getUserId());
        comment.setRating(rating);
        comment.setContent(content);
        comment.setCreateTime(LocalDateTime.now());
        comment.setStatus(1);
        commentMapper.updateById(comment);
        return Result.ok();
    }


}
