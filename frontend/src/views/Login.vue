<template>
  <div class="login-container">
    <div class="login-wrapper">
      <div class="login-card">
        <div class="login-header">
          <div class="login-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 1L3 5V11C3 16.55 6.84 21.74 12 23C17.16 21.74 21 16.55 21 11V5L12 1ZM12 11.99H7V10H12V7L16 10.5L12 14V11.99Z" fill="currentColor"/>
            </svg>
          </div>
          <h2>欢迎回来</h2>
          <p class="login-subtitle">登录您的账户</p>
        </div>
        
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label>用户名</label>
            <input 
              v-model="loginForm.username" 
              type="text" 
              required 
              placeholder="请输入用户名"
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label>密码</label>
            <input 
              v-model="loginForm.password" 
              type="password" 
              required 
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
          
          <button type="submit" :disabled="loading" class="login-btn">
            <span v-if="!loading">登录</span>
            <span v-else class="loading-state">
              <span class="spinner"></span>
              <span>登录中...</span>
            </span>
          </button>
        </form>
        
        <div class="login-footer">
          <p class="footer-text">还没有账号？</p>
          <router-link to="/register" class="register-link">
            <span>立即注册</span>
            <svg class="arrow-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 4L10.59 5.41L16.17 11H4V13H16.17L10.59 18.59L12 20L20 12L12 4Z" fill="currentColor"/>
            </svg>
          </router-link>
        </div>
      </div>
      
      <div class="login-decoration">
        <div class="decoration-circle circle-1"></div>
        <div class="decoration-circle circle-2"></div>
        <div class="decoration-circle circle-3"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { userApi } from '@/api'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    async handleLogin() {
      if (!this.loginForm.username || !this.loginForm.password) {
        alert('请填写完整信息')
        return
      }
      
      this.loading = true
      try {
        const response = await userApi.login(this.loginForm)
        if (response.code === 200) {
          // 存储token和用户信息
          const { token, user } = response.data
          localStorage.setItem('token', token)
          localStorage.setItem('userInfo', JSON.stringify(user))
          
          // 提示登录成功
          alert('登录成功')
          
          // 返回首页或跳转到管理页面
          this.$router.push('/admin')
        } else {
          alert(response.message || '登录失败')
        }
      } catch (error) {
        console.error('登录失败:', error)
        alert('登录失败，请检查用户名和密码')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.login-wrapper {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 420px;
}

.login-card {
  background: var(--card-bg);
  border-radius: 24px;
  padding: 40px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  border: 1px solid var(--border-color);
  backdrop-filter: blur(20px);
  position: relative;
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-light), var(--primary-color));
  background-size: 200% 100%;
  animation: gradientMove 3s ease infinite;
}

@keyframes gradientMove {
  0%, 100% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 16px;
  animation: float 3s ease-in-out infinite;
  color: var(--primary-color);
}

.login-icon svg {
  width: 100%;
  height: 100%;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.login-header h2 {
  margin: 0 0 8px 0;
  font-size: 28px;
  font-weight: 800;
  color: var(--text-primary);
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.login-subtitle {
  margin: 0;
  font-size: 14px;
  color: var(--text-secondary);
}

.login-form {
  margin-bottom: 24px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: var(--text-primary);
  font-size: 14px;
}

.form-input {
  width: 100%;
  padding: 14px 16px;
  border: 2px solid var(--border-color);
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.05);
  color: var(--text-primary);
  font-size: 15px;
  transition: all 0.3s ease;
  outline: none;
}

.form-input::placeholder {
  color: var(--text-secondary);
}

.form-input:focus {
  border-color: var(--primary-color);
  background: rgba(255, 255, 255, 0.1);
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
}

.login-btn {
  width: 100%;
  padding: 16px;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.4);
  position: relative;
  overflow: hidden;
}

.login-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.login-btn:hover::before {
  left: 100%;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.5);
}

.login-btn:active:not(:disabled) {
  transform: translateY(0);
}

.login-btn:disabled {
  background: linear-gradient(135deg, #64748b 0%, #475569 100%);
  cursor: not-allowed;
  opacity: 0.7;
}

.btn-icon {
  font-size: 18px;
}

.loading-state {
  display: flex;
  align-items: center;
  gap: 8px;
}

.spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.login-footer {
  text-align: center;
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
}

.footer-text {
  margin: 0 0 8px 0;
  font-size: 14px;
  color: var(--text-secondary);
}

.register-link {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s ease;
}

.register-link:hover {
  gap: 10px;
  color: var(--primary-light);
}

.register-link .arrow {
  font-size: 16px;
  transition: transform 0.3s ease;
}

.register-link:hover .arrow {
  transform: translateX(4px);
}

.register-link .arrow svg {
  width: 100%;
  height: 100%;
}

.login-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 0;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  animation: pulse 4s ease-in-out infinite;
}

.circle-1 {
  width: 300px;
  height: 300px;
  top: -100px;
  left: -100px;
  animation-delay: 0s;
}

.circle-2 {
  width: 200px;
  height: 200px;
  bottom: -50px;
  right: -50px;
  animation-delay: 1s;
}

.circle-3 {
  width: 150px;
  height: 150px;
  top: 50%;
  right: 10%;
  animation-delay: 2s;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.5;
  }
}

@media (max-width: 480px) {
  .login-card {
    padding: 30px 24px;
  }
  
  .login-icon {
    font-size: 40px;
  }
  
  .login-header h2 {
    font-size: 24px;
  }
}
</style>