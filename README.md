# 曹戈个人博客系统

这是一个基于Spring Boot + Vue的前后端分离个人博客系统，具有完整的博客功能。

## 项目结构

```
traetest1/                    # 后端Spring Boot项目
├── src/main/java/com/cycle/youth/traetest1/
│   ├── common/              # 通用类
│   │   └── Result.java      # 统一响应结果类
│   ├── config/              # 配置类
│   │   ├── WebConfig.java   # Web配置
│   │   ├── JwtAuthenticationFilter.java # JWT认证过滤器
│   │   └── SecurityConfig.java # 安全配置
│   ├── controller/          # 控制器层
│   │   ├── SysUserController.java    # 用户控制器
│   │   ├── BlogArticleController.java # 文章控制器
│   │   ├── BlogCategoryController.java # 分类控制器
│   │   └── BlogCommentController.java # 评论控制器
│   ├── entity/              # 实体类
│   │   ├── SysUser.java     # 用户实体
│   │   ├── BlogArticle.java # 文章实体
│   │   ├── BlogCategory.java # 分类实体
│   │   └── BlogComment.java # 评论实体
│   ├── mapper/              # 数据访问层
│   │   ├── SysUserMapper.java
│   │   ├── BlogArticleMapper.java
│   │   ├── BlogCategoryMapper.java
│   │   └── BlogCommentMapper.java
│   ├── service/             # 业务逻辑层
│   │   ├── impl/            # 实现类
│   │   └── 接口定义
│   ├── utils/               # 工具类
│   │   └── JwtUtil.java     # JWT工具类
│   ├── exception/           # 异常处理
│   │   └── GlobalExceptionHandler.java
│   └── Traetest1Application.java # 主应用类
├── src/main/resources/
│   └── application.properties # 应用配置
├── frontend/                # 前端Vue项目
│   ├── src/
│   │   ├── api/             # API接口定义
│   │   ├── assets/          # 静态资源
│   │   │   └── styles/      # 样式文件
│   │   ├── components/      # 组件
│   │   ├── router/          # 路由配置
│   │   ├── utils/           # 工具函数
│   │   ├── views/           # 页面视图
│   │   │   ├── Home.vue     # 首页
│   │   │   ├── ArticleDetail.vue # 文章详情页
│   │   │   ├── Category.vue # 分类页
│   │   │   ├── Login.vue    # 登录页
│   │   │   ├── Register.vue # 注册页
│   │   │   └── Admin.vue    # 管理后台
│   │   ├── App.vue          # 根组件
│   │   └── main.js          # 入口文件
│   ├── package.json         # 项目依赖
│   ├── vite.config.js       # Vite配置
│   └── index.html           # 入口HTML
├── pom.xml                  # Maven配置
└── README.md                # 项目说明
```

## 功能特性

### 后端 (Spring Boot)
- 用户管理：注册、登录、权限控制
- 文章管理：发布、编辑、删除文章
- 分类管理：创建、编辑、删除分类
- 评论管理：评论审核、回复、删除
- JWT身份验证
- MySQL数据库集成
- MyBatis-Plus ORM框架
- Spring Security安全框架

### 前端 (Vue 3)
- 响应式设计，支持移动端
- 首页展示文章列表
- 文章详情页及评论功能
- 分类浏览
- 用户登录/注册
- 管理后台（文章、分类、评论管理）
- 优雅的UI界面

## 技术栈

### 后端
- Java 21
- Spring Boot 4.x
- Spring Security
- Spring Data JPA
- MyBatis-Plus
- MySQL 8.0
- JWT
- Maven

### 前端
- Vue 3
- Vue Router 4
- Axios
- Element Plus (UI组件库)
- Vite
- CSS

## 快速开始

### 后端启动

1. 确保已安装Java 21和MySQL
2. 创建数据库：
   ```sql
   CREATE DATABASE blog_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. 修改 `src/main/resources/application.properties` 中的数据库连接信息
4. 启动后端服务：
   ```bash
   ./mvnw spring-boot:run
   ```

### 前端启动

1. 确保已安装Node.js
2. 安装依赖：
   ```bash
   cd frontend
   npm install
   ```
3. 启动开发服务器：
   ```bash
   npm run dev
   ```

## API接口文档

### 用户相关
- POST /api/user/login - 用户登录
- POST /api/user/register - 用户注册
- GET /api/user/info - 获取用户信息

### 文章相关
- GET /api/article/published - 获取所有发布文章
- GET /api/article/{id} - 获取文章详情
- POST /api/article/create - 创建文章
- PUT /api/article/update - 更新文章
- PUT /api/article/publish/{id} - 发布文章
- DELETE /api/article/{id} - 删除文章

### 分类相关
- GET /api/category/all - 获取所有分类
- POST /api/category/create - 创建分类
- PUT /api/category/update - 更新分类
- DELETE /api/category/{id} - 删除分类

### 评论相关
- GET /api/comment/article/{articleId} - 获取文章评论
- POST /api/comment/add - 添加评论
- PUT /api/comment/approve/{commentId}/{status} - 审核评论
- DELETE /api/comment/{commentId} - 删除评论

## 项目特点

1. **安全性**：使用JWT进行身份验证，防止未授权访问
2. **可扩展性**：采用模块化设计，易于功能扩展
3. **用户体验**：响应式界面设计，支持多种设备访问
4. **性能优化**：使用Redis缓存，提高访问速度
5. **代码质量**：遵循最佳实践，代码结构清晰

## 部署说明

前端构建：
```bash
npm run build
```

构建后的静态文件会输出到 `src/main/resources/static` 目录，与后端打包在一起部署。

## 作者

曹戈