import { Link } from 'react-router-dom'

const PAYMENTS = [
  '/images/payment_Icons/payment-1.webp',
  '/images/payment_Icons/payment-2.jpg',
  '/images/payment_Icons/payment-3.png',
  '/images/payment_Icons/payment-4.png',
]

export default function Footer() {
  return (
    <footer style={styles.footer}>
      <div style={styles.inner}>
        <div style={styles.brandCol}>
          <Link to="/" style={styles.logoWrap}>
            <img src="/images/store_logo/GroovyGean-logo.webp" alt="Canifa Shop" style={styles.logo} />
            <span style={styles.brandName}>Canifa Shop</span>
          </Link>
          <p style={styles.desc}>
            Thời trang thường ngày dễ mặc, chất liệu thoải mái và mức giá thân thiện.
          </p>
        </div>

        <div style={styles.col}>
          <h3 style={styles.title}>Mua sắm</h3>
          <Link to="/products" style={styles.link}>Sản phẩm</Link>
          <Link to="/cart" style={styles.link}>Giỏ hàng</Link>
          <Link to="/orders" style={styles.link}>Đơn hàng</Link>
        </div>

        <div style={styles.col}>
          <h3 style={styles.title}>Hỗ trợ</h3>
          <span style={styles.text}>Đổi trả trong 15 ngày</span>
          <span style={styles.text}>Giao hàng toàn quốc</span>
          <span style={styles.text}>Hotline: 1900 1000</span>
        </div>

        <div style={styles.col}>
          <h3 style={styles.title}>Thanh toán</h3>
          <div style={styles.paymentGrid}>
            {PAYMENTS.map((src) => (
              <div key={src} style={styles.paymentItem}>
                <img src={src} alt="Phương thức thanh toán" style={styles.paymentLogo} />
              </div>
            ))}
          </div>
        </div>
      </div>

      <div style={styles.bottom}>
        <span>© 2026 Canifa Shop. All rights reserved.</span>
      </div>
    </footer>
  )
}

const styles = {
  footer: {
    marginTop: '56px',
    background: '#111827',
    color: '#fff',
  },
  inner: {
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '40px 16px',
    display: 'grid',
    gridTemplateColumns: '1.6fr 1fr 1fr 1fr',
    gap: '28px',
  },
  brandCol: { display: 'flex', flexDirection: 'column', gap: '14px' },
  logoWrap: { display: 'flex', alignItems: 'center', gap: '10px', textDecoration: 'none', color: '#fff' },
  logo: { width: '42px', height: '42px', objectFit: 'contain', borderRadius: '6px', background: '#fff' },
  brandName: { fontWeight: 800, fontSize: '1.1rem' },
  desc: { color: '#d1d5db', maxWidth: '310px', fontSize: '0.9rem' },
  col: { display: 'flex', flexDirection: 'column', gap: '10px' },
  title: { fontSize: '0.95rem', marginBottom: '4px' },
  link: { color: '#d1d5db', textDecoration: 'none', fontSize: '0.9rem' },
  text: { color: '#d1d5db', fontSize: '0.9rem' },
  paymentGrid: { display: 'grid', gridTemplateColumns: 'repeat(2, 58px)', gap: '8px' },
  paymentItem: {
    height: '38px',
    borderRadius: '6px',
    background: '#fff',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '5px',
  },
  paymentLogo: { maxWidth: '100%', maxHeight: '100%', objectFit: 'contain' },
  bottom: {
    borderTop: '1px solid rgba(255,255,255,0.12)',
    color: '#9ca3af',
    fontSize: '0.82rem',
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '14px 16px 18px',
  },
}
