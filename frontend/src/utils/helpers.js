// 工具函数
export const utils = {
  // 格式化日期
  formatDate(dateString) {
    if (!dateString) return ''
    const date = new Date(dateString)
    return date.toLocaleDateString('zh-CN') + ' ' + date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  },

  // 截取字符串
  truncate(str, length = 100) {
    if (!str) return ''
    return str.length > length ? str.substring(0, length) + '...' : str
  },

  // 验证邮箱格式
  validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return re.test(email)
  },

  // 存储token到本地
  setToken(token) {
    localStorage.setItem('token', token)
  },

  // 获取本地token
  getToken() {
    return localStorage.getItem('token')
  },

  // 清除本地token
  removeToken() {
    localStorage.removeItem('token')
  },

  // 检查是否登录
  isLoggedIn() {
    return !!this.getToken()
  }
}