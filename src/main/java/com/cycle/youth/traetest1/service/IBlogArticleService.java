package com.cycle.youth.traetest1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cycle.youth.traetest1.entity.BlogArticle;
import java.util.List;

/**
 * <p>
 * 博客文章表 服务类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
public interface IBlogArticleService extends IService<BlogArticle> {
    /**
     * 获取所有发布的文章
     */
    List<BlogArticle> getAllPublishedArticles();

    /**
     * 根据分类获取文章
     */
    List<BlogArticle> getArticlesByCategory(Long categoryId);

    /**
     * 搜索文章
     */
    List<BlogArticle> searchArticles(String keyword);

    /**
     * 增加文章浏览量
     */
    void incrementViewCount(Long articleId);

    /**
     * 发布文章
     */
    boolean publishArticle(BlogArticle article);

    /**
     * 保存草稿
     */
    boolean saveDraft(BlogArticle article);
}