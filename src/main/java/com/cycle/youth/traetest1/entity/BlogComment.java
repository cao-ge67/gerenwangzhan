package com.cycle.youth.traetest1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 博客评论表
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_comment")
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 评论者姓名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 评论者邮箱
     */
    @TableField("author_email")
    private String authorEmail;

    /**
     * 评论者网站
     */
    @TableField("author_website")
    private String authorWebsite;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 父评论ID（用于回复评论）
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 审核状态 0待审核 1通过 2不通过
     */
    @TableField("status")
    private Integer status;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除 0未删除 1已删除
     */
    @TableField("deleted")
    private Integer deleted;

    public BlogComment() {
        this.status = 0; // 默认待审核
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.deleted = 0;
    }
}