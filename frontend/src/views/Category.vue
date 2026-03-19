<template>
  <div class="category-page">
    <div class="container">
      <div class="category-header">
        <h1 class="category-title">{{ categoryName }} 分类下的文章</h1>
        <p class="category-subtitle">浏览该分类下的所有文章</p>
      </div>
      
      <div class="articles-section">
        <div 
          v-for="article in articles" 
          :key="article.id" 
          class="article-card"
          @click="goToArticle(article.id)"
        >
          <div class="article-header">
            <h3 class="article-title">{{ article.title }}</h3>
            <div class="article-tags">
              <span class="article-tag">{{ categoryName }}</span>
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
          <p class="empty-text">该分类下暂无文章</p>
          <p class="empty-subtext">敬请期待更多精彩内容</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { articleApi, categoryApi } from '@/api'

export default {
  name: 'Category',
  props: ['id'],
  data() {
    return {
      articles: [],
      categoryName: ''
    }
  },
  async mounted() {
    console.log('Category mounted, id:', this.id)
    await this.loadCategory()
    await this.loadArticlesByCategory()
  },
  methods: {
    async loadCategory() {
      try {
        console.log('Loading category with id:', this.id)
        const response = await categoryApi.getCategoryById(this.id)
        console.log('Category response:', response)
        this.categoryName = response.data.name
      } catch (error) {
        console.error('加载分类失败:', error)
        // 不要自动跳转，让用户看到错误信息
        this.categoryName = '未知分类'
      }
    },
    async loadArticlesByCategory() {
      try {
        console.log('Loading articles for category:', this.id)
        const response = await articleApi.getArticlesByCategory(this.id)
        console.log('Articles response:', response)
        this.articles = response.data
      } catch (error) {
        console.error('加载文章失败:', error)
        this.articles = []
      }
    },
    goToArticle(id) {
      this.$router.push(`/article/${id}`)
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }
  }
}
</script>

<style scoped>
.category-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.category-header {
  text-align: center;
  padding: 40px 20px;
  margin-bottom: 30px;
}

.category-title {
  margin: 0 0 12px 0;
  font-size: 32px;
  font-weight: 800;
  color: var(--text-primary);
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.category-subtitle {
  margin: 0;
  font-size: 16px;
  color: var(--text-secondary);
}

.articles-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 800px;
  margin: 0 auto;
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

@media (max-width: 768px) {
  .category-header {
    padding: 30px 15px;
  }
  
  .category-title {
    font-size: 24px;
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