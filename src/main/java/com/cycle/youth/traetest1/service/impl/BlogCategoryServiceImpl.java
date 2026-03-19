package com.cycle.youth.traetest1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycle.youth.traetest1.entity.BlogCategory;
import com.cycle.youth.traetest1.mapper.BlogCategoryMapper;
import com.cycle.youth.traetest1.service.IBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客分类表 服务实现类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements IBlogCategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    public List<BlogCategory> getAllCategories() {
        QueryWrapper<BlogCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0); // 未删除
        wrapper.orderByAsc("sort_order"); // 按排序字段升序
        return blogCategoryMapper.selectList(wrapper);
    }

    @Override
    public BlogCategory getByAlias(String alias) {
        QueryWrapper<BlogCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("alias", alias);
        wrapper.eq("deleted", 0);
        return blogCategoryMapper.selectOne(wrapper);
    }

    @Override
    public List<BlogCategory> getCategoryWithArticleCount() {
        // 这里我们返回所有分类，并可以通过关联查询获得每个分类的文章数量
        // 实际实现中可能需要额外的查询或在数据库中维护计数
        return getAllCategories();
    }
}