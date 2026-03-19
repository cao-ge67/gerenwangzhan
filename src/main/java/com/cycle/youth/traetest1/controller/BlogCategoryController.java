package com.cycle.youth.traetest1.controller;

import com.cycle.youth.traetest1.common.Result;
import com.cycle.youth.traetest1.entity.BlogCategory;
import com.cycle.youth.traetest1.service.IBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客分类表 前端控制器
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@RestController
@RequestMapping("/api/category")
public class BlogCategoryController {

    @Autowired
    private IBlogCategoryService blogCategoryService;

    /**
     * 获取所有分类
     */
    @GetMapping("/all")
    public Result<List<BlogCategory>> getAllCategories() {
        List<BlogCategory> categories = blogCategoryService.getAllCategories();
        return Result.success(categories);
    }

    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public Result<BlogCategory> getCategoryById(@PathVariable Long id) {
        BlogCategory category = blogCategoryService.getById(id);
        if (category != null && category.getDeleted() == 0) {
            return Result.success(category);
        } else {
            return Result.error("分类不存在");
        }
    }

    /**
     * 根据别名获取分类
     */
    @GetMapping("/alias/{alias}")
    public Result<BlogCategory> getCategoryByAlias(@PathVariable String alias) {
        BlogCategory category = blogCategoryService.getByAlias(alias);
        if (category != null && category.getDeleted() == 0) {
            return Result.success(category);
        } else {
            return Result.error("分类不存在");
        }
    }

    /**
     * 创建分类（需要认证）
     */
    @PostMapping("/create")
    public Result<String> createCategory(@RequestBody BlogCategory category) {
        category.setCreateTime(java.time.LocalDateTime.now());
        category.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogCategoryService.save(category);
        if (success) {
            return Result.success("分类创建成功");
        } else {
            return Result.error("分类创建失败");
        }
    }

    /**
     * 更新分类（需要认证）
     */
    @PutMapping("/update")
    public Result<String> updateCategory(@RequestBody BlogCategory category) {
        category.setUpdateTime(java.time.LocalDateTime.now());
        boolean success = blogCategoryService.updateById(category);
        if (success) {
            return Result.success("分类更新成功");
        } else {
            return Result.error("分类更新失败");
        }
    }

    /**
     * 删除分类（需要认证）
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteCategory(@PathVariable Long id) {
        BlogCategory category = blogCategoryService.getById(id);
        if (category != null) {
            category.setDeleted(1); // 逻辑删除
            boolean success = blogCategoryService.updateById(category);
            if (success) {
                return Result.success("分类删除成功");
            } else {
                return Result.error("分类删除失败");
            }
        } else {
            return Result.error("分类不存在");
        }
    }
}