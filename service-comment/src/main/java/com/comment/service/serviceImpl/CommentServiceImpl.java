package com.comment.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.comment.domain.entity.Comment;
import com.comment.mapper.CommentMapper;
import com.comment.service.CommentService;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "comments")
@RequiredArgsConstructor()
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentMapper commentMapper;

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
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByActivityIdWithHighRatinig(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).ge(Comment::getRating,3).eq(Comment::getStatus,1);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByActivityIdWithLowRatinig(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).lt(Comment::getRating,3).eq(Comment::getStatus,1);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
    }

    @Override
    public Result getCommentByActivityIdWithAscTime(Long activityId, int page, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getActivityId, activityId).eq(Comment::getStatus,1);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> commentPage = new Page<>(page, size);
        return Result.ok(commentMapper.selectPage(commentPage, queryWrapper));
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
        comment.setCreateTime(LocalDateTime.now());
        comment.setStatus(1);
        commentService.updateById(comment);
        return Result.ok();
    }


}
