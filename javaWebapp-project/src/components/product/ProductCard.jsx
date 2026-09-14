import { Link } from 'react-router-dom'

// Props:
//   product — object từ API: { productId, name, description, category, brand, status }
//   mainImage — URL ảnh đại diện (lấy từ ProductImage có isMain = true)
//   price     — giá thấp nhất trong các variant

export default function ProductCard({ product, mainImage, price }) {
  // Format giá: 149000 → "149.000 ₫"
  const formatPrice = (p) =>
    p != null
      ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p)
      : 'Liên hệ'

  // Đường dẫn ảnh — Spring Boot serve static file từ /images/
  const imgSrc = mainImage
    ? normalizeImageUrl(mainImage)
    : 'https://placehold.co/300x380?text=No+Image'

  return (
    // Link bao toàn bộ card → click vào đâu cũng navigate đến trang chi tiết
    <Link to={`/products/${product.productId}`} style={styles.link}>
      <div style={styles.card}>
        <div style={styles.imgWrap}>
          <img src={imgSrc} alt={product.name} style={styles.img} />
        </div>

        <div style={styles.info}>
          {/* Tên thương hiệu */}
          <p style={styles.brand}>{product.brand?.name ?? ''}</p>

          {/* Tên sản phẩm — giới hạn 2 dòng */}
          <h3 style={styles.name}>{product.name}</h3>

          {/* Giá */}
          <p style={styles.price}>{formatPrice(price)}</p>
        </div>
      </div>
    </Link>
  )
}

function normalizeImageUrl(url) {
  if (url.startsWith('http') || url.startsWith('/')) return url
  return `/${url}`
}

const styles = {
  link: { textDecoration: 'none', color: 'inherit' },
  card: {
    backgroundColor: '#fff',
    borderRadius: '10px',
    overflow: 'hidden',
    border: '1px solid #e5e7eb',
    transition: 'box-shadow 0.2s',
    cursor: 'pointer',
  },
  imgWrap: {
    aspectRatio: '3/4',
    overflow: 'hidden',
    backgroundColor: '#f3f4f6',
  },
  img: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    transition: 'transform 0.3s',
  },
  info: { padding: '12px' },
  brand: { fontSize: '0.75rem', color: '#9ca3af', marginBottom: '4px', textTransform: 'uppercase' },
  name: {
    fontSize: '0.9rem', fontWeight: 500, color: '#111827',
    display: '-webkit-box', WebkitLineClamp: 2,
    WebkitBoxOrient: 'vertical', overflow: 'hidden',
    marginBottom: '8px', lineHeight: 1.4,
  },
  price: { fontSize: '0.95rem', fontWeight: 700, color: '#dc2626' },
}
