package com.cycle.youth.traetest1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cycle.youth.traetest1.entity.BlogCategory;
import java.util.List;

/**
 * <p>
 * 博客分类表 服务类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
public interface IBlogCategoryService extends IService<BlogCategory> {
    /**
     * 获取所有分类
     */
    List<BlogCategory> getAllCategories();

    /**
     * 根据别名获取分类
     */
    BlogCategory getByAlias(String alias);

    /**
     * 获取分类及其文章数量
     */
    List<BlogCategory> getCategoryWithArticleCount();
}