package com.cycle.youth.traetest1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycle.youth.traetest1.entity.BlogArticle;
import com.cycle.youth.traetest1.mapper.BlogArticleMapper;
import com.cycle.youth.traetest1.service.IBlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客文章表 服务实现类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public List<BlogArticle> getAllPublishedArticles() {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1); // 发布状态
        wrapper.eq("deleted", 0); // 未删除
        wrapper.orderByDesc("publish_time"); // 按发布时间倒序
        return blogArticleMapper.selectList(wrapper);
    }

    @Override
    public List<BlogArticle> getArticlesByCategory(Long categoryId) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        wrapper.eq("status", 1); // 发布状态
        wrapper.eq("deleted", 0); // 未删除
        wrapper.orderByDesc("publish_time");
        return blogArticleMapper.selectList(wrapper);
    }

    @Override
    public List<BlogArticle> searchArticles(String keyword) {
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.like("title", keyword).or().like("content", keyword).or().like("summary", keyword));
        wrapper.eq("status", 1); // 发布状态
        wrapper.eq("deleted", 0); // 未删除
        wrapper.orderByDesc("publish_time");
        return blogArticleMapper.selectList(wrapper);
    }

    @Override
    public void incrementViewCount(Long articleId) {
        BlogArticle article = blogArticleMapper.selectById(articleId);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            blogArticleMapper.updateById(article);
        }
    }

    @Override
    public boolean publishArticle(BlogArticle article) {
        article.setStatus(1); // 设置为发布状态
        article.setPublishTime(java.time.LocalDateTime.now()); // 设置发布时间
        int result = blogArticleMapper.updateById(article);
        return result > 0;
    }

    @Override
    public boolean saveDraft(BlogArticle article) {
        article.setStatus(0); // 设置为草稿状态
        int result = blogArticleMapper.updateById(article);
        return result > 0;
    }
}