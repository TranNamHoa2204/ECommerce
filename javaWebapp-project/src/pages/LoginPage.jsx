import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login as loginApi } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

// useState: biến + setter — giống let nhưng tự re-render khi đổi
// useNavigate: điều hướng bằng code sau khi login thành công

export default function LoginPage() {
  const [email, setEmail]       = useState('')
  const [password, setPassword] = useState('')
  const [error, setError]       = useState('')
  const [loading, setLoading]   = useState(false)

  const { login } = useAuth()       // hàm lưu token + user vào context
  const navigate  = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()   // ngăn form reload trang (giống JS thuần)
    setError('')
    setLoading(true)

    try {
      // Gọi POST /api/users/login
      const res = await loginApi(email, password)
      // API trả về { accessToken, tokenType, user: { userId, fullName, email, role, ... } }
      login(res.data.accessToken, res.data.user)
      navigate('/')   // về trang chủ sau khi đăng nhập
    } catch (err) {
      // Lấy message lỗi từ Spring Boot (GlobalExceptionHandler trả về { error: "..." })
      setError(err.response?.data?.error || 'Đăng nhập thất bại, vui lòng thử lại')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.wrapper}>
      <div style={styles.card}>
        {/* Logo / tiêu đề */}
        <div style={styles.header}>
          <h1 style={styles.title}>Đăng nhập</h1>
          <p style={styles.sub}>Chào mừng bạn trở lại Canifa Shop</p>
        </div>

        {/* Thông báo lỗi */}
        {error && <div style={styles.errorBox}>{error}</div>}

        {/* Form */}
        <form onSubmit={handleSubmit} style={styles.form}>
          <div style={styles.field}>
            <label style={styles.label}>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="example@email.com"
              required
              style={styles.input}
            />
          </div>

          <div style={styles.field}>
            <label style={styles.label}>Mật khẩu</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Nhập mật khẩu"
              required
              style={styles.input}
            />
          </div>

          <button type="submit" disabled={loading} style={styles.submitBtn}>
            {loading ? 'Đang đăng nhập...' : 'Đăng nhập'}
          </button>
        </form>

        {/* Chuyển sang đăng ký */}
        <p style={styles.footer}>
          Chưa có tài khoản?{' '}
          <Link to="/register" style={styles.footerLink}>Đăng ký ngay</Link>
        </p>
      </div>
    </div>
  )
}

const styles = {
  wrapper: {
    minHeight: '80vh', display: 'flex',
    alignItems: 'center', justifyContent: 'center',
  },
  card: {
    width: '100%', maxWidth: '420px',
    background: '#fff', borderRadius: '14px',
    border: '1px solid #e5e7eb', padding: '40px',
    boxShadow: '0 4px 24px rgba(0,0,0,0.06)',
  },
  header: { textAlign: 'center', marginBottom: '28px' },
  title: { fontSize: '1.6rem', fontWeight: 700, color: '#111827' },
  sub:   { color: '#6b7280', marginTop: '6px', fontSize: '0.9rem' },
  errorBox: {
    background: '#fef2f2', border: '1px solid #fecaca',
    color: '#dc2626', borderRadius: '8px',
    padding: '10px 14px', marginBottom: '20px', fontSize: '0.875rem',
  },
  form: { display: 'flex', flexDirection: 'column', gap: '18px' },
  field: { display: 'flex', flexDirection: 'column', gap: '6px' },
  label: { fontSize: '0.875rem', fontWeight: 500, color: '#374151' },
  input: {
    padding: '10px 14px', borderRadius: '8px',
    border: '1px solid #d1d5db', fontSize: '0.95rem',
    outline: 'none', transition: 'border-color 0.15s',
    width: '100%',
  },
  submitBtn: {
    marginTop: '8px', padding: '12px',
    background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px',
    fontSize: '1rem', fontWeight: 600,
    cursor: 'pointer', transition: 'opacity 0.15s',
  },
  footer: { textAlign: 'center', marginTop: '24px', fontSize: '0.875rem', color: '#6b7280' },
  footerLink: { color: '#111827', fontWeight: 600, textDecoration: 'none' },
}
