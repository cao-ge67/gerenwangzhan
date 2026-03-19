package com.cycle.youth.traetest1.controller;

import com.cycle.youth.traetest1.common.Result;
import com.cycle.youth.traetest1.entity.BlogArticle;
import com.cycle.youth.traetest1.service.IBlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客文章表 前端控制器
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@RestController
@RequestMapping("/api/article")
public class BlogArticleController {

    @Autowired
    private IBlogArticleService blogArticleService;

    /**
     * 获取所有发布的文章
     */
    @GetMapping("/published")
    public Result<List<BlogArticle>> getAllPublishedArticles() {
        List<BlogArticle> articles = blogArticleService.getAllPublishedArticles();
        return Result.success(articles);
    }

    /**
     * 根据ID获取文章详情
     */
    @GetMapping("/{id}")
    public Result<BlogArticle> getArticleById(@PathVariable Long id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null && article.getStatus() == 1 && article.getDeleted() == 0) {
            // 增加浏览量
            blogArticleService.incrementViewCount(id);
            return Result.success(article);
        } else {
            return Result.error("文章不存在或未发布");
        }
    }

    /**
     * 根据分类获取文章
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<BlogArticle>> getArticlesByCategory(@PathVariable Long categoryId) {
        List<BlogArticle> articles = blogArticleService.getArticlesByCategory(categoryId);
        return Result.success(articles);
    }

    /**
     * 搜索文章
     */
    @GetMapping("/search")
    public Result<List<BlogArticle>> searchArticles(@RequestParam String keyword) {
        List<BlogArticle> articles = blogArticleService.searchArticles(keyword);
        return Result.success(articles);
    }

    /**
     * 创建文章（需要认证）
     */
    @PostMapping("/create")
    public Result<String> createArticle(@RequestBody BlogArticle article) {
        article.setCreateTime(java.time.LocalDateTime.now());
        article.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogArticleService.save(article);
        if (success) {
            return Result.success("文章创建成功");
        } else {
            return Result.error("文章创建失败");
        }
    }

    /**
     * 更新文章（需要认证）
     */
    @PutMapping("/update")
    public Result<String> updateArticle(@RequestBody BlogArticle article) {
        article.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogArticleService.updateById(article);
        if (success) {
            return Result.success("文章更新成功");
        } else {
            return Result.error("文章更新失败");
        }
    }

    /**
     * 发布文章（需要认证）
     */
    @PutMapping("/publish/{id}")
    public Result<String> publishArticle(@PathVariable Long id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null) {
            boolean success = blogArticleService.publishArticle(article);
            if (success) {
                return Result.success("文章发布成功");
            } else {
                return Result.error("文章发布失败");
            }
        } else {
            return Result.error("文章不存在");
        }
    }

    /**
     * 保存草稿（需要认证）
     */
    @PutMapping("/draft/{id}")
    public Result<String> saveDraft(@PathVariable Long id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null) {
            boolean success = blogArticleService.saveDraft(article);
            if (success) {
                return Result.success("草稿保存成功");
            } else {
                return Result.error("草稿保存失败");
            }
        } else {
            return Result.error("文章不存在");
        }
    }

    /**
     * 删除文章（需要认证）
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteArticle(@PathVariable Long id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null) {
            article.setDeleted(1); // 逻辑删除
            boolean success = blogArticleService.updateById(article);
            if (success) {
                return Result.success("文章删除成功");
            } else {
                return Result.error("文章删除失败");
            }
        } else {
            return Result.error("文章不存在");
        }
    }
}