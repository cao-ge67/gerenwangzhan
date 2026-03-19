-- 创建数据库
CREATE DATABASE IF NOT EXISTS blog_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE blog_db;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像地址',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0未删除 1已删除'
) COMMENT='用户表';

-- 博客分类表
CREATE TABLE IF NOT EXISTS blog_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    alias VARCHAR(50) NOT NULL COMMENT '分类别名',
    description TEXT COMMENT '描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0未删除 1已删除'
) COMMENT='博客分类表';

-- 博客文章表
CREATE TABLE IF NOT EXISTS blog_article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL COMMENT '文章标题',
    content LONGTEXT NOT NULL COMMENT '文章内容',
    summary TEXT COMMENT '文章摘要',
    cover_image VARCHAR(255) COMMENT '封面图片',
    category_id BIGINT COMMENT '分类ID',
    tags JSON COMMENT '标签(JSON格式)',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    status TINYINT DEFAULT 0 COMMENT '发布状态 0草稿 1发布',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0未删除 1已删除'
) COMMENT='博客文章表';

-- 博客评论表
CREATE TABLE IF NOT EXISTS blog_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL COMMENT '评论内容',
    author_name VARCHAR(50) NOT NULL COMMENT '评论者姓名',
    author_email VARCHAR(100) NOT NULL COMMENT '评论者邮箱',
    author_website VARCHAR(100) COMMENT '评论者网站',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID（用于回复评论）',
    status TINYINT DEFAULT 0 COMMENT '审核状态 0待审核 1通过 2不通过',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0未删除 1已删除'
) COMMENT='博客评论表';

-- 创建索引
CREATE INDEX idx_article_category ON blog_article(category_id);
CREATE INDEX idx_article_status ON blog_article(status);
CREATE INDEX idx_article_deleted ON blog_article(deleted);
CREATE INDEX idx_comment_article ON blog_comment(article_id);
CREATE INDEX idx_comment_status ON blog_comment(status);
CREATE INDEX idx_comment_deleted ON blog_comment(deleted);

-- 插入默认管理员用户 (密码为明文'admin123'的BCrypt哈希值)
INSERT INTO sys_user (username, password, nickname, email, role) VALUES 
('admin', '$2a$10$8K1mQJgYF5kZqP2R1O4WBeq4p3a5r6s7t8u9v0w1x2y3z4A5B6C7D', '管理员', 'admin@example.com', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

-- 插入默认分类
INSERT INTO blog_category (name, alias, description) VALUES 
('技术分享', 'tech', '技术相关的文章分享'),
('生活感悟', 'life', '生活中的感悟和思考'),
('随笔杂谈', 'essay', '各种随笔和杂谈')
ON DUPLICATE KEY UPDATE name=name;