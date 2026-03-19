import axios from 'axios'

// 创建axios实例
const service = axios.create({
  baseURL: '/api', // 所有请求都会带上/api前缀，由vite代理到后端
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么，比如添加token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    const result = response.data
    if (result.code === 200) {
      return result
    } else {
      console.error('API Error:', result.message)
      return Promise.reject(new Error(result.message || '请求失败'))
    }
  },
  error => {
    // 对响应错误做点什么
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

export default service