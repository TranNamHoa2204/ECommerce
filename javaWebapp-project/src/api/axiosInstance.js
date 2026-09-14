import axios from 'axios'

// Tạo một axios instance dùng chung cho cả app
// baseURL trống vì Vite proxy đã xử lý /api → localhost:8080
const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
})

// Request interceptor: tự gắn JWT token vào mọi request
// (giống như bạn set header trước mỗi fetch() trong JS thuần)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Response interceptor: nếu token hết hạn (401) → tự logout
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default api
