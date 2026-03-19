package com.cycle.youth.traetest1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cycle.youth.traetest1.entity.BlogComment;
import java.util.List;

/**
 * <p>
 * 博客评论表 服务类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
public interface IBlogCommentService extends IService<BlogComment> {
    /**
     * 获取文章的所有评论
     */
    List<BlogComment> getCommentsByArticleId(Long articleId);

    /**
     * 添加评论
     */
    boolean addComment(BlogComment comment);

    /**
     * 回复评论
     */
    boolean replyComment(BlogComment comment);

    /**
     * 审核评论
     */
    boolean approveComment(Long commentId, Integer status);

    /**
     * 删除评论
     */
    boolean deleteComment(Long commentId);
}