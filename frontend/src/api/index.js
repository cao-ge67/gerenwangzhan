// API接口定义
import request from '@/utils/request'

// 用户相关API
export const userApi = {
  // 登录
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  // 注册
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },
  // 获取用户信息
  getUserInfo(username) {
    return request({
      url: '/user/info',
      method: 'get',
      params: { username }
    })
  }
}

// 文章相关API
export const articleApi = {
  // 获取所有发布文章
  getAllPublishedArticles() {
    return request({
      url: '/article/published',
      method: 'get'
    })
  },
  // 根据ID获取文章详情
  getArticleById(id) {
    return request({
      url: `/article/${id}`,
      method: 'get'
    })
  },
  // 根据分类获取文章
  getArticlesByCategory(categoryId) {
    return request({
      url: `/article/category/${categoryId}`,
      method: 'get'
    })
  },
  // 搜索文章
  searchArticles(keyword) {
    return request({
      url: '/article/search',
      method: 'get',
      params: { keyword }
    })
  },
  // 创建文章
  createArticle(data) {
    return request({
      url: '/article/create',
      method: 'post',
      data
    })
  },
  // 更新文章
  updateArticle(data) {
    return request({
      url: '/article/update',
      method: 'put',
      data
    })
  },
  // 发布文章
  publishArticle(id) {
    return request({
      url: `/article/publish/${id}`,
      method: 'put'
    })
  },
  // 保存草稿
  saveDraft(id) {
    return request({
      url: `/article/draft/${id}`,
      method: 'put'
    })
  },
  // 删除文章
  deleteArticle(id) {
    return request({
      url: `/article/${id}`,
      method: 'delete'
    })
  }
}

// 分类相关API
export const categoryApi = {
  // 获取所有分类
  getAllCategories() {
    return request({
      url: '/category/all',
      method: 'get'
    })
  },
  // 根据ID获取分类
  getCategoryById(id) {
    return request({
      url: `/category/${id}`,
      method: 'get'
    })
  },
  // 根据别名获取分类
  getCategoryByAlias(alias) {
    return request({
      url: `/category/alias/${alias}`,
      method: 'get'
    })
  },
  // 创建分类
  createCategory(data) {
    return request({
      url: '/category/create',
      method: 'post',
      data
    })
  },
  // 更新分类
  updateCategory(data) {
    return request({
      url: '/category/update',
      method: 'put',
      data
    })
  },
  // 删除分类
  deleteCategory(id) {
    return request({
      url: `/category/${id}`,
      method: 'delete'
    })
  }
}

// 评论相关API
export const commentApi = {
  // 获取文章评论
  getCommentsByArticleId(articleId) {
    return request({
      url: `/comment/article/${articleId}`,
      method: 'get'
    })
  },
  // 添加评论
  addComment(data) {
    return request({
      url: '/comment/add',
      method: 'post',
      data
    })
  },
  // 回复评论
  replyComment(data) {
    return request({
      url: '/comment/reply',
      method: 'post',
      data
    })
  },
  // 审核评论
  approveComment(commentId, status) {
    return request({
      url: `/comment/approve/${commentId}/${status}`,
      method: 'put'
    })
  },
  // 删除评论
  deleteComment(commentId) {
    return request({
      url: `/comment/${commentId}`,
      method: 'delete'
    })
  }
}