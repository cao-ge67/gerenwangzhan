<template>
  <div class="home">
    <header class="blog-header">
      <div class="header-content">
        <div class="logo">
          <h1>曹戈的个人博客</h1>
          <p class="tagline">分享技术，记录生活</p>
        </div>
        <nav class="main-nav">
          <router-link to="/" class="nav-link">首页</router-link>
          <router-link to="/admin" v-if="isLoggedIn" class="nav-link">管理</router-link>
          <router-link to="/login" v-else class="nav-link">登录</router-link>
          <router-link to="/register" v-if="!isLoggedIn" class="nav-link">注册</router-link>
        </nav>
      </div>
    </header>

    <div class="container">
      <div class="search-section">
        <div class="search-box">
          <input 
            v-model="searchKeyword" 
            placeholder="搜索文章..." 
            @keyup.enter="searchArticles"
            class="search-input"
          >
          <button @click="searchArticles" class="search-btn">搜索</button>
        </div>
      </div>

      <div class="content-wrapper">
        <aside class="sidebar">
          <div class="sidebar-card categories-card">
            <h3 class="sidebar-title">文章分类</h3>
            <div class="category-list">
              <div 
                v-for="category in categories" 
                :key="category.id" 
                class="category-item"
                @click="goToCategory(category.id)"
              >
                <span class="category-name">{{ category.name }}</span>
                <svg class="category-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 4L10.59 5.41L16.17 11H4V13H16.17L10.59 18.59L12 20L20 12L12 4Z" fill="currentColor"/>
                </svg>
              </div>
            </div>
          </div>
        </aside>

        <main class="main-content">
          <div class="articles-section">
            <h2 class="section-title">最新文章</h2>
            <div 
              v-for="article in articles" 
              :key="article.id" 
              class="article-card"
              @click="goToArticle(article.id)"
            >
              <div class="article-header">
                <h3 class="article-title">{{ article.title }}</h3>
                <div class="article-tags">
                  <span v-if="article.categoryId" class="article-tag">
                    {{ getCategoryName(article.categoryId) }}
                  </span>
                </div>
              </div>
              <p class="article-summary">{{ article.summary }}</p>
              <div class="article-footer">
                <div class="article-stats">
                  <span class="stat-item">
                    {{ formatDate(article.publishTime) }}
                  </span>
                  <span class="stat-item">
                    {{ article.viewCount }} 浏览
                  </span>
                  <span class="stat-item">
                    {{ article.commentCount }} 评论
                  </span>
                </div>
                <div class="article-action">
                  <span class="read-more">阅读全文</span>
                  <svg class="arrow" width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M12 4L10.59 5.41L16.17 11H4V13H16.17L10.59 18.59L12 20L20 12L12 4Z" fill="currentColor"/>
                  </svg>
                </div>
              </div>
            </div>
            
            <div v-if="articles.length === 0" class="empty-state">
              <p class="empty-text">暂无文章</p>
              <p class="empty-subtext">敬请期待更多精彩内容</p>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script>
import { articleApi, categoryApi } from '@/api'

export default {
  name: 'Home',
  data() {
    return {
      articles: [],
      categories: [],
      searchKeyword: '',
      isLoggedIn: false
    }
  },
  async mounted() {
    await this.loadArticles()
    await this.loadCategories()
    this.checkLoginStatus()
  },
  methods: {
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
        console.log('Categories response:', response)
        this.categories = response.data
      } catch (error) {
        console.error('加载分类失败:', error)
      }
    },
    goToArticle(id) {
      this.$router.push(`/article/${id}`)
    },
    goToCategory(id) {
      console.log('Going to category:', id)
      console.log('Current route:', this.$route)
      this.$router.push(`/category/${id}`)
    },
    async searchArticles() {
      if (!this.searchKeyword.trim()) {
        await this.loadArticles()
        return
      }
      
      try {
        const response = await articleApi.searchArticles(this.searchKeyword)
        this.articles = response.data
      } catch (error) {
        console.error('搜索文章失败:', error)
      }
    },
    getCategoryName(categoryId) {
      const category = this.categories.find(cat => cat.id === categoryId)
      return category ? category.name : ''
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    },
    checkLoginStatus() {
      const token = localStorage.getItem('token')
      this.isLoggedIn = !!token
    }
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  background: url('https://i.bobopic.com/small/63883126.jpg') no-repeat center center fixed;
  background-size: cover;
}

.home::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(15, 23, 42, 0.75);
  z-index: -1;
}

.blog-header {
  background: rgba(15, 23, 42, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo h1 {
  margin: 0;
  font-size: 28px;
  background: linear-gradient(135deg, #f59e0b 0%, #ffffff 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-weight: 800;
  letter-spacing: -0.5px;
}

.tagline {
  margin: 4px 0 0 0;
  font-size: 14px;
  color: var(--text-secondary);
  font-weight: 400;
}

.main-nav {
  display: flex;
  gap: 8px;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 10px 16px;
  border-radius: 10px;
  color: var(--text-primary);
  font-weight: 500;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.nav-link:hover {
  background: rgba(99, 102, 241, 0.2);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
  border-color: rgba(99, 102, 241, 0.5);
}

.search-section {
  padding: 30px 15px;
}

.search-box {
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  gap: 10px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  padding: 8px;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

.search-input {
  flex: 1;
  padding: 12px 16px;
  border: none;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.1);
  color: var(--text-primary);
  font-size: 15px;
  outline: none;
  transition: all 0.3s ease;
}

.search-input::placeholder {
  color: var(--text-secondary);
}

.search-input:focus {
  background: rgba(255, 255, 255, 0.15);
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.3);
}

.search-btn {
  padding: 12px 24px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  color: white;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.4);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(99, 102, 241, 0.5);
}

.content-wrapper {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 30px;
  padding: 0 15px 30px;
  max-width: 1200px;
  margin: 0 auto;
}

.sidebar {
  position: sticky;
  top: 100px;
  height: fit-content;
}

.sidebar-card {
  background: var(--card-bg);
  border-radius: 16px;
  padding: 24px;
  border: 1px solid var(--border-color);
  backdrop-filter: blur(10px);
  box-shadow: var(--shadow-xl);
  margin-bottom: 20px;
}

.sidebar-title {
  margin: 0 0 20px 0;
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.category-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.category-item:hover {
  background: rgba(99, 102, 241, 0.15);
  transform: translateX(4px);
  border-color: rgba(99, 102, 241, 0.3);
}

.category-name {
  flex: 1;
  font-weight: 500;
  color: var(--text-primary);
}

.category-arrow {
  color: var(--text-secondary);
  transition: transform 0.3s ease;
}

.category-item:hover .category-arrow {
  transform: translateX(4px);
  color: var(--primary-color);
}

.category-arrow svg {
  width: 100%;
  height: 100%;
}

.main-content {
  min-width: 0;
}

.section-title {
  margin: 0 0 24px 0;
  font-size: 24px;
  font-weight: 800;
  color: var(--text-primary);
}

.articles-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  background: var(--card-bg);
  border-radius: 16px;
  padding: 24px;
  border: 1px solid var(--border-color);
  backdrop-filter: blur(10px);
  box-shadow: var(--shadow-lg);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.article-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(180deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.article-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
  border-color: rgba(99, 102, 241, 0.3);
}

.article-card:hover::before {
  opacity: 1;
}

.article-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
  gap: 12px;
}

.article-title {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1.4;
  flex: 1;
}

.article-tags {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.article-tag {
  padding: 6px 12px;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  color: white;
  border-radius: 8px;
  font-size: 12px;
  font-weight: 600;
  white-space: nowrap;
}

.article-summary {
  color: var(--text-secondary);
  line-height: 1.7;
  margin: 0 0 20px 0;
  font-size: 15px;
}

.article-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 16px;
  border-top: 1px solid var(--border-color);
}

.article-stats {
  display: flex;
  gap: 20px;
}

.stat-item {
  color: var(--text-secondary);
  font-size: 13px;
}

.article-action {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s ease;
}

.article-card:hover .article-action {
  gap: 12px;
}

.arrow {
  font-size: 18px;
  transition: transform 0.3s ease;
}

.article-card:hover .arrow {
  transform: translateX(4px);
}

.arrow svg {
  width: 100%;
  height: 100%;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
  background: var(--card-bg);
  border-radius: 16px;
  border: 1px solid var(--border-color);
  backdrop-filter: blur(10px);
}

.empty-text {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 8px 0;
}

.empty-subtext {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
}

@media (max-width: 1024px) {
  .content-wrapper {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .sidebar {
    position: static;
  }
  
  .sidebar-card {
    margin-bottom: 20px;
  }
}

@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }
  
  .main-nav {
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .search-box {
    flex-direction: column;
  }
  
  .search-btn {
    width: 100%;
    justify-content: center;
  }
  
  .article-header {
    flex-direction: column;
  }
  
  .article-footer {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }
  
  .article-stats {
    flex-wrap: wrap;
    gap: 12px;
  }
}
</style>