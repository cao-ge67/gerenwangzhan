<template>
  <div class="article-detail">
    <div v-if="article" class="article-content">
      <h1>{{ article.title }}</h1>
      <div class="article-meta">
        <span>发布时间: {{ formatDate(article.publishTime) }}</span>
        <span>浏览: {{ article.viewCount }}</span>
        <span>分类: {{ categoryName }}</span>
      </div>
      <div class="article-body" v-html="article.content"></div>
    </div>
    
    <div v-else class="loading">文章加载中...</div>

    <!-- 评论区 -->
    <div class="comments-section">
      <h3>评论</h3>
      
      <!-- 发表评论 -->
      <div class="comment-form">
        <textarea 
          v-model="newComment.content" 
          placeholder="请输入您的评论..."
          rows="4"
        ></textarea>
        <div class="comment-inputs">
          <input v-model="newComment.authorName" placeholder="姓名*" required>
          <input v-model="newComment.authorEmail" placeholder="邮箱*" type="email" required>
          <input v-model="newComment.authorWebsite" placeholder="网站">
        </div>
        <button @click="submitComment" :disabled="!canSubmitComment">发表评论</button>
      </div>

      <!-- 评论列表 -->
      <div class="comments-list">
        <div 
          v-for="comment in comments" 
          :key="comment.id" 
          class="comment-item"
        >
          <div class="comment-header">
            <strong>{{ comment.authorName }}</strong>
            <span class="comment-time">{{ formatDate(comment.createTime) }}</span>
          </div>
          <div class="comment-content">{{ comment.content }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { articleApi, commentApi, categoryApi } from '@/api'

export default {
  name: 'ArticleDetail',
  props: ['id'],
  data() {
    return {
      article: null,
      categoryName: '',
      comments: [],
      newComment: {
        content: '',
        authorName: '',
        authorEmail: '',
        authorWebsite: '',
        articleId: this.id
      }
    }
  },
  computed: {
    canSubmitComment() {
      return this.newComment.content && 
             this.newComment.authorName && 
             this.newComment.authorEmail
    }
  },
  async mounted() {
    await this.loadArticle()
    await this.loadComments()
  },
  methods: {
    async loadArticle() {
      try {
        const response = await articleApi.getArticleById(this.id)
        this.article = response.data
        
        // 加载分类名称
        if (this.article.categoryId) {
          const categoryResponse = await categoryApi.getCategoryById(this.article.categoryId)
          this.categoryName = categoryResponse.data.name
        }
      } catch (error) {
        console.error('加载文章失败:', error)
        this.$router.push('/')
      }
    },
    async loadComments() {
      try {
        const response = await commentApi.getCommentsByArticleId(this.id)
        this.comments = response.data
      } catch (error) {
        console.error('加载评论失败:', error)
      }
    },
    async submitComment() {
      if (!this.canSubmitComment) return
      
      try {
        this.newComment.articleId = this.id
        await commentApi.addComment(this.newComment)
        
        // 清空表单
        this.newComment.content = ''
        this.newComment.authorName = ''
        this.newComment.authorEmail = ''
        this.newComment.authorWebsite = ''
        
        // 重新加载评论
        await this.loadComments()
      } catch (error) {
        console.error('提交评论失败:', error)
      }
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN') + ' ' + date.toLocaleTimeString('zh-CN')
    }
  }
}
</script>

<style scoped>
.article-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.article-content h1 {
  color: #333;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.article-meta {
  display: flex;
  gap: 20px;
  color: #888;
  font-size: 14px;
  margin: 15px 0;
}

.article-body {
  line-height: 1.8;
  color: #444;
  margin: 20px 0;
}

.comments-section {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.comment-form {
  margin-bottom: 30px;
}

.comment-form textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: vertical;
  margin-bottom: 10px;
}

.comment-inputs {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
  flex-wrap: wrap;
}

.comment-inputs input {
  flex: 1;
  min-width: 150px;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.comment-form button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-form button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.comments-list {
  margin-top: 20px;
}

.comment-item {
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 15px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.comment-time {
  color: #888;
  font-size: 12px;
}

.comment-content {
  color: #555;
  line-height: 1.5;
}
</style>