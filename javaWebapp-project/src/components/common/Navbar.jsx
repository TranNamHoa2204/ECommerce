import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

// Link = thẻ <a> của React Router, không reload trang khi click
// useNavigate = điều hướng bằng code (giống window.location.href)

export default function Navbar() {
  const { user, logout, isLoggedIn } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <nav style={styles.nav}>
      <div style={styles.brand}>
        <Link to="/" style={styles.brandLink}>
          <img src="/images/store_logo/GroovyGean-logo.webp" alt="Canifa Shop" style={styles.logo} />
          <span>Canifa Shop</span>
        </Link>
      </div>

      <div style={styles.links}>
        <Link to="/" style={styles.link}>Trang chủ</Link>
        <Link to="/products" style={styles.link}>Sản phẩm</Link>

        {isLoggedIn ? (
          <>
            <Link to="/cart"    style={styles.link}>🛒 Giỏ hàng</Link>
            <Link to="/orders"  style={styles.link}>Đơn hàng</Link>
            <Link to="/profile" style={styles.link}>Tài khoản</Link>
            <span style={styles.username}>Xin chào, {user.fullName}</span>
            <button onClick={handleLogout} style={styles.btn}>Đăng xuất</button>
          </>
        ) : (
          <>
            <Link to="/login" style={styles.link}>Đăng nhập</Link>
            <Link to="/register" style={styles.link}>Đăng ký</Link>
          </>
        )}
      </div>
    </nav>
  )
}

const styles = {
  nav: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '12px 32px',
    backgroundColor: '#fff',
    borderBottom: '1px solid #e5e7eb',
    position: 'sticky',
    top: 0,
    zIndex: 100,
  },
  brand: { fontWeight: 700, fontSize: '1.25rem' },
  brandLink: { display: 'flex', alignItems: 'center', gap: '10px', textDecoration: 'none', color: '#111' },
  logo: { width: '36px', height: '36px', objectFit: 'contain', borderRadius: '6px' },
  links: { display: 'flex', gap: '20px', alignItems: 'center' },
  link: { textDecoration: 'none', color: '#374151', fontSize: '0.95rem' },
  username: { color: '#6b7280', fontSize: '0.9rem' },
  btn: {
    background: 'none', border: '1px solid #d1d5db', borderRadius: '6px',
    padding: '4px 12px', cursor: 'pointer', color: '#374151',
  },
}
