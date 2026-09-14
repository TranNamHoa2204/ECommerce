import { createContext, useContext, useState } from 'react'

// Context = "kho dữ liệu dùng chung" cho cả app
// Thay vì truyền props qua nhiều cấp component, dùng context
const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  // Khởi tạo từ localStorage (để refresh trang không bị mất đăng nhập)
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user')
    return saved ? JSON.parse(saved) : null
  })

  // Gọi sau khi login thành công: lưu token + user vào localStorage
  const login = (token, userData) => {
    localStorage.setItem('token', token)
    localStorage.setItem('user', JSON.stringify(userData))
    setUser(userData)
  }

  // Gọi khi logout: xóa localStorage, reset state
  const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setUser(null)
  }

  const isLoggedIn = !!user
  const isAdmin = user?.role === 'ADMIN'

  return (
    <AuthContext.Provider value={{ user, login, logout, isLoggedIn, isAdmin }}>
      {children}
    </AuthContext.Provider>
  )
}

// Custom hook để dùng AuthContext — gọn hơn useContext(AuthContext) ở mọi nơi
export function useAuth() {
  return useContext(AuthContext)
}
