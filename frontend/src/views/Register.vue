<template>
  <div class="register-container">
    <div class="register-wrapper">
      <div class="register-card">
        <div class="register-header">
          <div class="register-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM12 20C7.59 20 4 16.41 4 12C4 7.59 7.59 4 12 4C16.41 4 20 7.59 20 12C20 16.41 16.41 20 12 20ZM15 11H13V9C13 8.45 12.55 8 12 8C11.45 8 11 8.45 11 9V11H9C8.45 11 8 11.45 8 12C8 12.55 8.45 13 9 13H11V15C11 15.55 11.45 16 12 16C12.55 16 13 15.55 13 15V13H15C15.55 13 16 12.55 16 12C16 11.45 15.55 11 15 11Z" fill="currentColor"/>
            </svg>
          </div>
          <h2>创建账户</h2>
          <p class="register-subtitle">加入我们的社区</p>
        </div>
        
        <form @submit.prevent="handleRegister" class="register-form">
          <div class="form-group">
            <label>用户名</label>
            <input 
              v-model="registerForm.username" 
              type="text" 
              required 
              placeholder="请输入用户名"
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label>昵称</label>
            <input 
              v-model="registerForm.nickname" 
              type="text" 
              required 
              placeholder="请输入昵称"
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label>邮箱</label>
            <input 
              v-model="registerForm.email" 
              type="email" 
              required 
              placeholder="请输入邮箱"
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label>密码</label>
            <input 
              v-model="registerForm.password" 
              type="password" 
              required 
              placeholder="请输入密码"
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label>确认密码</label>
            <input 
              v-model="confirmPassword" 
              type="password" 
              required 
              placeholder="请再次输入密码"
              class="form-input"
            >
          </div>
          
          <button type="submit" :disabled="loading" class="register-btn">
            <span v-if="!loading">注册</span>
            <span v-else class="loading-state">
              <span class="spinner"></span>
              <span>注册中...</span>
            </span>
          </button>
        </form>
        
        <div class="register-footer">
          <p class="footer-text">已有账号？</p>
          <router-link to="/login" class="login-link">
            <span>立即登录</span>
            <svg class="arrow-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 4L10.59 5.41L16.17 11H4V13H16.17L10.59 18.59L12 20L20 12L12 4Z" fill="currentColor"/>
            </svg>
          </router-link>
        </div>
      </div>
      
      <div class="register-decoration">
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
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        nickname: '',
        email: '',
        password: ''
      },
      confirmPassword: '',
      loading: false
    }
  },
  methods: {
    async handleRegister() {
      // 验证密码一致性
      if (this.registerForm.password !== this.confirmPassword) {
        alert('两次输入的密码不一致')
        return
      }
      
      if (!this.registerForm.username || !this.registerForm.nickname || 
          !this.registerForm.email || !this.registerForm.password) {
        alert('请填写完整信息')
        return
      }
      
      this.loading = true
      try {
        const response = await userApi.register(this.registerForm)
        if (response.code === 200) {
          alert('注册成功，请登录')
          this.$router.push('/login')
        } else {
          alert(response.message || '注册失败')
        }
      } catch (error) {
        console.error('注册失败:', error)
        alert('注册失败，请稍后重试')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url('https://i.bobopic.com/small/63883126.jpg') no-repeat center center fixed;
  background-size: cover;
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.register-container::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(15, 23, 42, 0.75);
  z-index: -1;
}

.register-wrapper {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 420px;
}

.register-card {
  background: var(--card-bg);
  border-radius: 24px;
  padding: 40px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  border: 1px solid var(--border-color);
  backdrop-filter: blur(20px);
  position: relative;
  overflow: hidden;
}

.register-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, var(--secondary-color), var(--primary-color), var(--secondary-color));
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

.register-header {
  text-align: center;
  margin-bottom: 32px;
}

.register-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 16px;
  animation: float 3s ease-in-out infinite;
  color: var(--primary-color);
}

.register-icon svg {
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

.register-header h2 {
  margin: 0 0 8px 0;
  font-size: 28px;
  font-weight: 800;
  color: var(--text-primary);
  background: linear-gradient(135deg, var(--secondary-color) 0%, var(--primary-color) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.register-subtitle {
  margin: 0;
  font-size: 14px;
  color: var(--text-secondary);
}

.register-form {
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
  border-color: var(--secondary-color);
  background: rgba(255, 255, 255, 0.1);
  box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
}

.register-btn {
  width: 100%;
  padding: 16px;
  background: linear-gradient(135deg, var(--secondary-color) 0%, #059669 100%);
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
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
  position: relative;
  overflow: hidden;
}

.register-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.register-btn:hover::before {
  left: 100%;
}

.register-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.5);
}

.register-btn:active:not(:disabled) {
  transform: translateY(0);
}

.register-btn:disabled {
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

.register-footer {
  text-align: center;
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
}

.footer-text {
  margin: 0 0 8px 0;
  font-size: 14px;
  color: var(--text-secondary);
}

.login-link {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s ease;
}

.login-link:hover {
  gap: 10px;
  color: var(--primary-light);
}

.login-link .arrow {
  font-size: 16px;
  transition: transform 0.3s ease;
}

.login-link:hover .arrow {
  transform: translateX(4px);
}

.login-link .arrow svg {
  width: 100%;
  height: 100%;
}

.register-decoration {
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
  .register-card {
    padding: 30px 24px;
  }
  
  .register-icon {
    font-size: 40px;
  }
  
  .register-header h2 {
    font-size: 24px;
  }
}
</style>