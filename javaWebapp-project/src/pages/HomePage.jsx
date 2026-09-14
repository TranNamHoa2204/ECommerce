import { Link } from 'react-router-dom'

const CATEGORIES = [
  { label: 'Áo Phông', image: '/images/banner/bodybanner-1.jpg' },
  { label: 'Áo Polo', image: '/images/products/1009/blue-1.webp' },
  { label: 'Áo sơ mi', image: '/images/products/1024/blue-1.webp' },
  { label: 'Quần shorts', image: '/images/products/1013/grey-1.webp' },
  { label: 'Quần dài', image: '/images/products/1030/beige-1.webp' },
  { label: 'Áo khoác', image: '/images/products/1037/blue-1.webp' },
]

const FEATURES = [
  { title: 'Đổi trả 15 ngày', text: 'Linh hoạt cho sản phẩm còn nguyên tem mác.' },
  { title: 'Giao hàng nhanh', text: 'Đóng gói chỉn chu, theo dõi đơn dễ dàng.' },
  { title: 'Thanh toán an toàn', text: 'Hỗ trợ nhiều phương thức thanh toán phổ biến.' },
]

export default function HomePage() {
  return (
    <div style={styles.page}>
      <section style={styles.hero}>
        <img
          src="/images/banner/topbanner-1.webp"
          alt="Bộ sưu tập thời trang mới"
          style={styles.heroImg}
        />
        <div style={styles.heroOverlay} />
        <div style={styles.heroContent}>
          <p style={styles.kicker}>New season essentials</p>
          <h1 style={styles.heroTitle}>Canifa Shop</h1>
          <p style={styles.heroSub}>
            Trang phục hằng ngày thoải mái, hiện đại và dễ phối cho cả gia đình.
          </p>
          <Link to="/products" style={styles.heroBtn}>
            Mua sắm ngay
          </Link>
        </div>
      </section>

      <section style={styles.featureGrid}>
        {FEATURES.map((item) => (
          <div key={item.title} style={styles.featureItem}>
            <strong style={styles.featureTitle}>{item.title}</strong>
            <span style={styles.featureText}>{item.text}</span>
          </div>
        ))}
      </section>

      <section style={styles.section}>
        <div style={styles.sectionHead}>
          <h2 style={styles.sectionTitle}>Danh mục nổi bật</h2>
          <Link to="/products" style={styles.sectionLink}>Xem tất cả</Link>
        </div>

        <div style={styles.catGrid}>
          {CATEGORIES.map((cat) => (
            <Link
              key={cat.label}
              to="/products"
              style={styles.catCard}
            >
              <img src={cat.image} alt={cat.label} style={styles.catImg} />
              <span style={styles.catLabel}>{cat.label}</span>
            </Link>
          ))}
        </div>
      </section>

      <section style={styles.bannerGrid}>
        <Link to="/products" style={styles.promoCard}>
          <img src="/images/banner/bodybanner-2.jpg" alt="Thời trang nữ" style={styles.promoImg} />
          <div style={styles.promoContent}>
            <span style={styles.promoSmall}>Everyday comfort</span>
            <strong style={styles.promoTitle}>Áo phông, polo và sơ mi dễ mặc</strong>
          </div>
        </Link>

        <Link to="/products" style={styles.promoCard}>
          <img src="/images/banner/bodybanner-4.jpg" alt="Thời trang năng động" style={styles.promoImg} />
          <div style={styles.promoContent}>
            <span style={styles.promoSmall}>Active mood</span>
            <strong style={styles.promoTitle}>Trang phục nhẹ, gọn cho ngày bận rộn</strong>
          </div>
        </Link>
      </section>

      <section style={styles.cta}>
        <div>
          <p style={styles.ctaKicker}>Hơn 40 mẫu có sẵn</p>
          <h2 style={styles.ctaTitle}>Tìm outfit mới cho tuần này</h2>
        </div>
        <Link to="/products" style={styles.ctaBtn}>Khám phá sản phẩm</Link>
      </section>
    </div>
  )
}

const styles = {
  page: { display: 'flex', flexDirection: 'column', gap: '48px' },
  hero: {
    position: 'relative',
    minHeight: '440px',
    overflow: 'hidden',
    borderRadius: '8px',
    background: '#111827',
  },
  heroImg: {
    width: '100%',
    height: '100%',
    minHeight: '440px',
    objectFit: 'cover',
  },
  heroOverlay: {
    position: 'absolute',
    inset: 0,
    background: 'linear-gradient(90deg, rgba(17,24,39,0.78), rgba(17,24,39,0.2) 62%, rgba(17,24,39,0.08))',
  },
  heroContent: {
    position: 'absolute',
    left: '48px',
    bottom: '48px',
    maxWidth: '500px',
    color: '#fff',
  },
  kicker: { fontSize: '0.78rem', textTransform: 'uppercase', letterSpacing: '0.08em', marginBottom: '10px' },
  heroTitle: { fontSize: '3rem', fontWeight: 800, lineHeight: 1.05, marginBottom: '14px' },
  heroSub: { fontSize: '1.05rem', color: '#f3f4f6', marginBottom: '28px' },
  heroBtn: {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '44px',
    padding: '0 24px',
    background: '#fff',
    color: '#111827',
    borderRadius: '6px',
    textDecoration: 'none',
    fontWeight: 700,
  },
  featureGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)',
    gap: '16px',
  },
  featureItem: {
    background: '#fff',
    border: '1px solid #e5e7eb',
    borderRadius: '8px',
    padding: '18px 20px',
    display: 'flex',
    flexDirection: 'column',
    gap: '4px',
  },
  featureTitle: { fontSize: '0.95rem' },
  featureText: { color: '#6b7280', fontSize: '0.86rem' },
  section: { display: 'flex', flexDirection: 'column', gap: '18px' },
  sectionHead: { display: 'flex', alignItems: 'center', justifyContent: 'space-between', gap: '16px' },
  sectionTitle: { fontSize: '1.35rem', fontWeight: 800 },
  sectionLink: { color: '#dc2626', textDecoration: 'none', fontWeight: 700, fontSize: '0.9rem' },
  catGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))',
    gap: '16px',
  },
  catCard: {
    position: 'relative',
    height: '190px',
    overflow: 'hidden',
    borderRadius: '8px',
    background: '#fff',
    border: '1px solid #e5e7eb',
    textDecoration: 'none',
    color: '#111827',
  },
  catImg: { width: '100%', height: '100%', objectFit: 'cover' },
  catLabel: {
    position: 'absolute',
    left: '12px',
    right: '12px',
    bottom: '12px',
    minHeight: '34px',
    borderRadius: '6px',
    background: 'rgba(255,255,255,0.92)',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontWeight: 700,
    fontSize: '0.9rem',
  },
  bannerGrid: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: '20px',
  },
  promoCard: {
    position: 'relative',
    minHeight: '280px',
    overflow: 'hidden',
    borderRadius: '8px',
    textDecoration: 'none',
    color: '#fff',
    background: '#111827',
  },
  promoImg: { width: '100%', height: '100%', minHeight: '280px', objectFit: 'cover' },
  promoContent: {
    position: 'absolute',
    left: '24px',
    right: '24px',
    bottom: '24px',
    display: 'flex',
    flexDirection: 'column',
    gap: '6px',
    textShadow: '0 1px 18px rgba(0,0,0,0.55)',
  },
  promoSmall: { fontSize: '0.78rem', textTransform: 'uppercase', letterSpacing: '0.08em' },
  promoTitle: { fontSize: '1.35rem', lineHeight: 1.2 },
  cta: {
    background: '#fff',
    border: '1px solid #e5e7eb',
    borderRadius: '8px',
    padding: '28px 32px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    gap: '20px',
  },
  ctaKicker: { color: '#dc2626', fontWeight: 700, fontSize: '0.85rem', marginBottom: '4px' },
  ctaTitle: { fontSize: '1.55rem', lineHeight: 1.2 },
  ctaBtn: {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '44px',
    padding: '0 22px',
    background: '#111827',
    color: '#fff',
    borderRadius: '6px',
    textDecoration: 'none',
    fontWeight: 700,
    whiteSpace: 'nowrap',
  },
}
