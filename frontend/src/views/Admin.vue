<template>
  <div class="admin-panel">
    <header class="admin-header">
      <h1>管理后台</h1>
      <button @click="logout" class="logout-btn">退出登录</button>
    </header>

    <div class="admin-content">
      <div class="sidebar">
        <ul>
          <li :class="{ active: activeTab === 'dashboard' }" @click="activeTab = 'dashboard'">仪表盘</li>
          <li :class="{ active: activeTab === 'articles' }" @click="activeTab = 'articles'">文章管理</li>
          <li :class="{ active: activeTab === 'categories' }" @click="activeTab = 'categories'">分类管理</li>
          <li :class="{ active: activeTab === 'comments' }" @click="activeTab = 'comments'">评论管理</li>
        </ul>
      </div>

      <div class="main-content">
        <!-- 仪表盘 -->
        <div v-if="activeTab === 'dashboard'" class="dashboard">
          <h2>仪表盘</h2>
          <div class="stats">
            <div class="stat-card">
              <h3>总文章数</h3>
              <p>{{ stats.totalArticles }}</p>
            </div>
            <div class="stat-card">
              <h3>总分类数</h3>
              <p>{{ stats.totalCategories }}</p>
            </div>
            <div class="stat-card">
              <h3>总评论数</h3>
              <p>{{ stats.totalComments }}</p>
            </div>
            <div class="stat-card">
              <h3>总浏览量</h3>
              <p>{{ stats.totalViews }}</p>
            </div>
          </div>
        </div>

        <!-- 文章管理 -->
        <div v-if="activeTab === 'articles'" class="articles-management">
          <h2>文章管理</h2>
          
          <div class="actions">
            <button @click="showCreateArticleModal = true" class="btn-primary">新增文章</button>
          </div>
          
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>标题</th>
                <th>分类</th>
                <th>状态</th>
                <th>浏览量</th>
                <th>发布时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="article in articles" :key="article.id">
                <td>{{ article.id }}</td>
                <td>{{ article.title }}</td>
                <td>{{ getCategoryName(article.categoryId) }}</td>
                <td>{{ article.status === 1 ? '已发布' : '草稿' }}</td>
                <td>{{ article.viewCount }}</td>
                <td>{{ formatDate(article.publishTime) }}</td>
                <td>
                  <button @click="editArticle(article)" class="btn-edit">编辑</button>
                  <button @click="toggleArticleStatus(article)" class="btn-status">
                    {{ article.status === 1 ? '取消发布' : '发布' }}
                  </button>
                  <button @click="deleteArticle(article.id)" class="btn-delete">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 分类管理 -->
        <div v-if="activeTab === 'categories'" class="categories-management">
          <h2>分类管理</h2>
          
          <div class="actions">
            <button @click="showCreateCategoryModal = true" class="btn-primary">新增分类</button>
          </div>
          
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>名称</th>
                <th>别名</th>
                <th>描述</th>
                <th>排序</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="category in categories" :key="category.id">
                <td>{{ category.id }}</td>
                <td>{{ category.name }}</td>
                <td>{{ category.alias }}</td>
                <td>{{ category.description }}</td>
                <td>{{ category.sortOrder }}</td>
                <td>
                  <button @click="editCategory(category)" class="btn-edit">编辑</button>
                  <button @click="deleteCategory(category.id)" class="btn-delete">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 评论管理 -->
        <div v-if="activeTab === 'comments'" class="comments-management">
          <h2>评论管理</h2>
          
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>内容</th>
                <th>作者</th>
                <th>邮箱</th>
                <th>文章</th>
                <th>状态</th>
                <th>时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="comment in comments" :key="comment.id">
                <td>{{ comment.id }}</td>
                <td>{{ comment.content.substring(0, 30) }}...</td>
                <td>{{ comment.authorName }}</td>
                <td>{{ comment.authorEmail }}</td>
                <td>{{ getArticleTitle(comment.articleId) }}</td>
                <td>
                  <span :class="'status-' + comment.status">
                    {{ comment.status === 1 ? '已通过' : comment.status === 2 ? '未通过' : '待审核' }}
                  </span>
                </td>
                <td>{{ formatDate(comment.createTime) }}</td>
                <td>
                  <button v-if="comment.status === 0" @click="approveComment(comment.id, 1)" class="btn-approve">通过</button>
                  <button v-if="comment.status === 0" @click="approveComment(comment.id, 2)" class="btn-reject">拒绝</button>
                  <button @click="deleteComment(comment.id)" class="btn-delete">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- 创建/编辑文章模态框 -->
    <div v-if="showCreateArticleModal" class="modal-overlay" @click="closeArticleModal">
      <div class="modal-content" @click.stop>
        <h3>{{ editingArticle ? '编辑文章' : '创建文章' }}</h3>
        <form @submit.prevent="saveArticle">
          <div class="form-group">
            <label>标题:</label>
            <input v-model="currentArticle.title" type="text" required>
          </div>
          <div class="form-group">
            <label>摘要:</label>
            <textarea v-model="currentArticle.summary" rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>内容:</label>
            <textarea v-model="currentArticle.content" rows="10" required></textarea>
          </div>
          <div class="form-group">
            <label>分类:</label>
            <select v-model="currentArticle.categoryId">
              <option value="">请选择分类</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <button type="button" @click="closeArticleModal" class="btn-cancel">取消</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 创建/编辑分类模态框 -->
    <div v-if="showCreateCategoryModal" class="modal-overlay" @click="closeCategoryModal">
      <div class="modal-content" @click.stop>
        <h3>{{ editingCategory ? '编辑分类' : '创建分类' }}</h3>
        <form @submit.prevent="saveCategory">
          <div class="form-group">
            <label>名称:</label>
            <input v-model="currentCategory.name" type="text" required>
          </div>
          <div class="form-group">
            <label>别名:</label>
            <input v-model="currentCategory.alias" type="text" required>
          </div>
          <div class="form-group">
            <label>描述:</label>
            <textarea v-model="currentCategory.description" rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>排序:</label>
            <input v-model.number="currentCategory.sortOrder" type="number" min="0">
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <button type="button" @click="closeCategoryModal" class="btn-cancel">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { articleApi, categoryApi, commentApi } from '@/api'

export default {
  name: 'Admin',
  data() {
    return {
      activeTab: 'dashboard',
      articles: [],
      categories: [],
      comments: [],
      stats: {
        totalArticles: 0,
        totalCategories: 0,
        totalComments: 0,
        totalViews: 0
      },
      showCreateArticleModal: false,
      showCreateCategoryModal: false,
      editingArticle: false,
      editingCategory: false,
      currentArticle: {
        id: null,
        title: '',
        summary: '',
        content: '',
        categoryId: null
      },
      currentCategory: {
        id: null,
        name: '',
        alias: '',
        description: '',
        sortOrder: 0
      }
    }
  },
  async mounted() {
    await this.loadData()
  },
  methods: {
    async loadData() {
      await Promise.all([
        this.loadArticles(),
        this.loadCategories(),
        this.loadComments()
      ])
      this.calculateStats()
    },
    async loadArticles() {
      try {
        const response = await articleApi.getAllPublishedArticles()
        this.articles = response.data
      } catch (error) {
        console.error('加载文章失败:', error)
      }
    },
    async loadCategories() {
      try {
        const response = await categoryApi.getAllCategories()
        this.categories = response.data
      } catch (error) {
        console.error('加载分类失败:', error)
      }
    },
    async loadComments() {
      // 由于没有获取所有评论的API，这里模拟加载一些评论
      this.comments = []
    },
    calculateStats() {
      this.stats.totalArticles = this.articles.length
      this.stats.totalCategories = this.categories.length
      this.stats.totalComments = this.comments.length
      this.stats.totalViews = this.articles.reduce((sum, article) => sum + article.viewCount, 0)
    },
    getCategoryName(categoryId) {
      if (!categoryId) return '未分类'
      const category = this.categories.find(cat => cat.id === categoryId)
      return category ? category.name : '未知分类'
    },
    getArticleTitle(articleId) {
      const article = this.articles.find(art => art.id === articleId)
      return article ? article.title : '未知文章'
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    },
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      this.$router.push('/')
    },
    editArticle(article) {
      this.currentArticle = { ...article }
      this.editingArticle = true
      this.showCreateArticleModal = true
    },
    async toggleArticleStatus(article) {
      try {
        if (article.status === 1) {
          // 如果是发布状态，则存为草稿
          await articleApi.saveDraft(article.id)
          article.status = 0
        } else {
          // 如果是草稿状态，则发布
          await articleApi.publishArticle(article.id)
          article.status = 1
        }
        alert('操作成功')
      } catch (error) {
        console.error('操作失败:', error)
        alert('操作失败')
      }
    },
    async deleteArticle(id) {
      if (confirm('确定要删除这篇文章吗？')) {
        try {
          await articleApi.deleteArticle(id)
          this.articles = this.articles.filter(article => article.id !== id)
          alert('删除成功')
        } catch (error) {
          console.error('删除失败:', error)
          alert('删除失败')
        }
      }
    },
    closeArticleModal() {
      this.showCreateArticleModal = false
      this.editingArticle = false
      this.currentArticle = {
        id: null,
        title: '',
        summary: '',
        content: '',
        categoryId: null
      }
    },
    async saveArticle() {
      try {
        if (this.editingArticle) {
          await articleApi.updateArticle(this.currentArticle)
          // 更新本地数组
          const index = this.articles.findIndex(a => a.id === this.currentArticle.id)
          if (index !== -1) {
            this.articles[index] = { ...this.currentArticle }
          }
        } else {
          const response = await articleApi.createArticle(this.currentArticle)
          // 重新加载文章列表
          await this.loadArticles()
        }
        
        this.closeArticleModal()
        alert('保存成功')
      } catch (error) {
        console.error('保存失败:', error)
        alert('保存失败')
      }
    },
    editCategory(category) {
      this.currentCategory = { ...category }
      this.editingCategory = true
      this.showCreateCategoryModal = true
    },
    async deleteCategory(id) {
      if (confirm('确定要删除这个分类吗？')) {
        try {
          await categoryApi.deleteCategory(id)
          this.categories = this.categories.filter(category => category.id !== id)
          alert('删除成功')
        } catch (error) {
          console.error('删除失败:', error)
          alert('删除失败')
        }
      }
    },
    closeCategoryModal() {
      this.showCreateCategoryModal = false
      this.editingCategory = false
      this.currentCategory = {
        id: null,
        name: '',
        alias: '',
        description: '',
        sortOrder: 0
      }
    },
    async saveCategory() {
      try {
        if (this.editingCategory) {
          await categoryApi.updateCategory(this.currentCategory)
          // 更新本地数组
          const index = this.categories.findIndex(c => c.id === this.currentCategory.id)
          if (index !== -1) {
            this.categories[index] = { ...this.currentCategory }
          }
        } else {
          await categoryApi.createCategory(this.currentCategory)
          // 重新加载分类列表
          await this.loadCategories()
        }
        
        this.closeCategoryModal()
        alert('保存成功')
      } catch (error) {
        console.error('保存失败:', error)
        alert('保存失败')
      }
    },
    async approveComment(commentId, status) {
      try {
        await commentApi.approveComment(commentId, status)
        // 更新本地评论列表
        const comment = this.comments.find(c => c.id === commentId)
        if (comment) {
          comment.status = status
        }
        alert('操作成功')
      } catch (error) {
        console.error('操作失败:', error)
        alert('操作失败')
      }
    },
    async deleteComment(commentId) {
      if (confirm('确定要删除这条评论吗？')) {
        try {
          await commentApi.deleteComment(commentId)
          this.comments = this.comments.filter(comment => comment.id !== commentId)
          alert('删除成功')
        } catch (error) {
          console.error('删除失败:', error)
          alert('删除失败')
        }
      }
    }
  }
}
</script>

<style scoped>
.admin-panel {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: url('https://i.bobopic.com/small/63883126.jpg') no-repeat center center fixed;
  background-size: cover;
  position: relative;
}

.admin-panel::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(15, 23, 42, 0.75);
  z-index: -1;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
  background-color: #343a40;
  color: white;
}

.logout-btn {
  padding: 8px 16px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.admin-content {
  display: flex;
  flex: 1;
}

.sidebar {
  width: 200px;
  background-color: #f8f9fa;
  border-right: 1px solid #dee2e6;
  padding: 20px 0;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 12px 20px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.sidebar li:hover,
.sidebar li.active {
  background-color: #007bff;
  color: white;
}

.main-content {
  flex: 1;
  padding: 20px;
}

.dashboard {
  padding: 20px 0;
}

.stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.stat-card {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  padding: 20px;
  text-align: center;
}

.stat-card h3 {
  margin: 0 0 10px 0;
  color: #6c757d;
  font-size: 14px;
}

.stat-card p {
  margin: 0;
  font-size: 24px;
  font-weight: bold;
  color: #495057;
}

.actions {
  margin-bottom: 20px;
}

.btn-primary {
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-edit {
  padding: 4px 8px;
  background-color: #ffc107;
  color: #212529;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 12px;
}

.btn-status {
  padding: 4px 8px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 12px;
}

.btn-delete {
  padding: 4px 8px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.btn-approve {
  padding: 4px 8px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 12px;
}

.btn-reject {
  padding: 4px 8px;
  background-color: #ffc107;
  color: #212529;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
}

.btn-cancel {
  padding: 8px 16px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.data-table th,
.data-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #dee2e6;
}

.data-table th {
  background-color: #f8f9fa;
  font-weight: bold;
}

.status-0 {
  color: #ffc107;
}

.status-1 {
  color: #28a745;
}

.status-2 {
  color: #dc3545;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>