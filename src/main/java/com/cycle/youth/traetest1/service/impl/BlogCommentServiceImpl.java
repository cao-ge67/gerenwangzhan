package com.cycle.youth.traetest1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycle.youth.traetest1.entity.BlogComment;
import com.cycle.youth.traetest1.mapper.BlogCommentMapper;
import com.cycle.youth.traetest1.service.IBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客评论表 服务实现类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public List<BlogComment> getCommentsByArticleId(Long articleId) {
        QueryWrapper<BlogComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.eq("status", 1); // 只获取已审核通过的评论
        wrapper.eq("deleted", 0); // 未删除
        wrapper.orderByDesc("create_time"); // 按创建时间倒序
        return blogCommentMapper.selectList(wrapper);
    }

    @Override
    public boolean addComment(BlogComment comment) {
        comment.setParentId(0L); // 新增评论父ID为0
        int result = blogCommentMapper.insert(comment);
        return result > 0;
    }

    @Override
    public boolean replyComment(BlogComment comment) {
        // 回复评论时，parentId不为0
        int result = blogCommentMapper.insert(comment);
        return result > 0;
    }

    @Override
    public boolean approveComment(Long commentId, Integer status) {
        BlogComment comment = blogCommentMapper.selectById(commentId);
        if (comment != null) {
            comment.setStatus(status);
            int result = blogCommentMapper.updateById(comment);
            return result > 0;
        }
        return false;
    }

    @Override
    public boolean deleteComment(Long commentId) {
        BlogComment comment = blogCommentMapper.selectById(commentId);
        if (comment != null) {
            comment.setDeleted(1); // 逻辑删除
            int result = blogCommentMapper.updateById(comment);
            return result > 0;
        }
        return false;
    }
}