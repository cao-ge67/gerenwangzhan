package com.cycle.youth.traetest1.controller;

import com.cycle.youth.traetest1.common.Result;
import com.cycle.youth.traetest1.entity.BlogComment;
import com.cycle.youth.traetest1.service.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客评论表 前端控制器
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@RestController
@RequestMapping("/api/comment")
public class BlogCommentController {

    @Autowired
    private IBlogCommentService blogCommentService;

    /**
     * 获取文章的所有评论
     */
    @GetMapping("/article/{articleId}")
    public Result<List<BlogComment>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<BlogComment> comments = blogCommentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    /**
     * 添加评论
     */
    @PostMapping("/add")
    public Result<String> addComment(@RequestBody BlogComment comment) {
        comment.setCreateTime(java.time.LocalDateTime.now());
        comment.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogCommentService.addComment(comment);
        if (success) {
            return Result.success("评论添加成功");
        } else {
            return Result.error("评论添加失败");
        }
    }

    /**
     * 回复评论
     */
    @PostMapping("/reply")
    public Result<String> replyComment(@RequestBody BlogComment comment) {
        comment.setCreateTime(java.time.LocalDateTime.now());
        comment.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogCommentService.replyComment(comment);
        if (success) {
            return Result.success("回复添加成功");
        } else {
            return Result.error("回复添加失败");
        }
    }

    /**
     * 审核评论（需要认证）
     */
    @PutMapping("/approve/{commentId}/{status}")
    public Result<String> approveComment(@PathVariable Long commentId, @PathVariable Integer status) {
        boolean success = blogCommentService.approveComment(commentId, status);
        if (success) {
            String msg = status == 1 ? "评论审核通过" : "评论审核不通过";
            return Result.success(msg);
        } else {
            return Result.error("评论审核失败");
        }
    }

    /**
     * 删除评论（需要认证）
     */
    @DeleteMapping("/{commentId}")
    public Result<String> deleteComment(@PathVariable Long commentId) {
        boolean success = blogCommentService.deleteComment(commentId);
        if (success) {
            return Result.success("评论删除成功");
        } else {
            return Result.error("评论删除失败");
        }
    }
}