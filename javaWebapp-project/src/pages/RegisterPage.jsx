import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register as registerApi } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

export default function RegisterPage() {
  const [form, setForm]       = useState({ fullName: '', email: '', password: '', confirmPassword: '', phone: '' })
  const [errors, setErrors]   = useState({})   // lỗi từng field
  const [serverError, setServerError] = useState('')
  const [loading, setLoading] = useState(false)

  const { login } = useAuth()
  const navigate  = useNavigate()

  // Cập nhật một field trong form — pattern phổ biến trong React
  // thay vì 5 hàm setter riêng
  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
    // Xóa lỗi của field khi user bắt đầu sửa
    if (errors[name]) setErrors((prev) => ({ ...prev, [name]: '' }))
  }

  // Validate phía client trước khi gọi API
  const validate = () => {
    const newErrors = {}
    if (!form.fullName.trim()) newErrors.fullName = 'Họ tên không được để trống'
    if (!form.email.trim())    newErrors.email    = 'Email không được để trống'
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email))
      newErrors.email = 'Email không hợp lệ'
    if (!form.password)        newErrors.password = 'Mật khẩu không được để trống'
    else if (form.password.length < 6)
      newErrors.password = 'Mật khẩu phải có ít nhất 6 ký tự'
    if (form.password !== form.confirmPassword)
      newErrors.confirmPassword = 'Mật khẩu xác nhận không khớp'
    if (!form.phone.trim())    newErrors.phone = 'Số điện thoại không được để trống'
    else if (!/^0\d{9}$/.test(form.phone))
      newErrors.phone = 'Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số'
    return newErrors
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setServerError('')

    // Validate trước — nếu có lỗi thì không gọi API
    const validationErrors = validate()
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors)
      return
    }

    setLoading(true)
    try {
      // POST /api/users/register
      // API trả về UserResponseDTO (không có token) — cần login ngay sau đó
      await registerApi(form.fullName, form.email, form.password, form.phone)

      // Tự động đăng nhập sau khi đăng ký thành công
      const { login: loginApi } = await import('../api/authApi')
      const loginRes = await loginApi(form.email, form.password)
      login(loginRes.data.accessToken, loginRes.data.user)

      navigate('/')
    } catch (err) {
      // Server trả về lỗi (vd: email đã tồn tại)
      setServerError(err.response?.data?.error || 'Đăng ký thất bại, vui lòng thử lại')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={styles.wrapper}>
      <div style={styles.card}>
        <div style={styles.header}>
          <h1 style={styles.title}>Tạo tài khoản</h1>
          <p style={styles.sub}>Đăng ký để mua sắm tại Canifa Shop</p>
        </div>

        {serverError && <div style={styles.errorBox}>{serverError}</div>}

        <form onSubmit={handleSubmit} style={styles.form} noValidate>

          <Field label="Họ và tên" error={errors.fullName}>
            <input
              name="fullName" type="text"
              value={form.fullName} onChange={handleChange}
              placeholder="Nguyễn Văn A"
              style={{ ...styles.input, ...(errors.fullName ? styles.inputError : {}) }}
            />
          </Field>

          <Field label="Email" error={errors.email}>
            <input
              name="email" type="email"
              value={form.email} onChange={handleChange}
              placeholder="example@email.com"
              style={{ ...styles.input, ...(errors.email ? styles.inputError : {}) }}
            />
          </Field>

          <Field label="Số điện thoại" error={errors.phone}>
            <input
              name="phone" type="tel"
              value={form.phone} onChange={handleChange}
              placeholder="0912345678"
              style={{ ...styles.input, ...(errors.phone ? styles.inputError : {}) }}
            />
          </Field>

          <Field label="Mật khẩu" error={errors.password}>
            <input
              name="password" type="password"
              value={form.password} onChange={handleChange}
              placeholder="Ít nhất 6 ký tự"
              style={{ ...styles.input, ...(errors.password ? styles.inputError : {}) }}
            />
          </Field>

          <Field label="Xác nhận mật khẩu" error={errors.confirmPassword}>
            <input
              name="confirmPassword" type="password"
              value={form.confirmPassword} onChange={handleChange}
              placeholder="Nhập lại mật khẩu"
              style={{ ...styles.input, ...(errors.confirmPassword ? styles.inputError : {}) }}
            />
          </Field>

          <button type="submit" disabled={loading} style={styles.submitBtn}>
            {loading ? 'Đang đăng ký...' : 'Tạo tài khoản'}
          </button>
        </form>

        <p style={styles.footer}>
          Đã có tài khoản?{' '}
          <Link to="/login" style={styles.footerLink}>Đăng nhập</Link>
        </p>
      </div>
    </div>
  )
}

// Component nội bộ — bọc input + label + error message
// Tái sử dụng trong cùng file, không cần export
function Field({ label, error, children }) {
  return (
    <div style={fieldStyles.wrapper}>
      <label style={fieldStyles.label}>{label}</label>
      {children}
      {error && <span style={fieldStyles.error}>{error}</span>}
    </div>
  )
}

const styles = {
  wrapper: {
    minHeight: '80vh', display: 'flex',
    alignItems: 'center', justifyContent: 'center',
    padding: '24px 0',
  },
  card: {
    width: '100%', maxWidth: '460px',
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
  form: { display: 'flex', flexDirection: 'column', gap: '16px' },
  input: {
    width: '100%', padding: '10px 14px',
    borderRadius: '8px', border: '1px solid #d1d5db',
    fontSize: '0.95rem', outline: 'none',
  },
  inputError: { borderColor: '#f87171' },
  submitBtn: {
    marginTop: '8px', padding: '12px',
    background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px',
    fontSize: '1rem', fontWeight: 600,
    cursor: 'pointer',
  },
  footer: { textAlign: 'center', marginTop: '24px', fontSize: '0.875rem', color: '#6b7280' },
  footerLink: { color: '#111827', fontWeight: 600, textDecoration: 'none' },
}

const fieldStyles = {
  wrapper: { display: 'flex', flexDirection: 'column', gap: '5px' },
  label: { fontSize: '0.875rem', fontWeight: 500, color: '#374151' },
  error: { fontSize: '0.8rem', color: '#dc2626' },
}
