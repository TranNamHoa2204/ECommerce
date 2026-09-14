import { useEffect, useMemo, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { addToCart } from '../api/cartApi'
import { getProductById } from '../api/productApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../context/AuthContext'

// ─── Trang chi tiết sản phẩm ─────────────────────────────────────────────────
// Luồng chọn: Màu → lọc ảnh + lọc size còn hàng → chọn Size → hiện giá/tồn kho → Thêm giỏ

export default function ProductDetailPage() {
  const { id }        = useParams()        // lấy :id từ URL /products/1001
  const navigate      = useNavigate()
  const { isLoggedIn } = useAuth()

  const [product, setProduct]   = useState(null)
  const [loading, setLoading]   = useState(true)
  const [error, setError]       = useState(null)

  // Lựa chọn của user
  const [selectedColor, setSelectedColor] = useState(null)
  const [selectedSize, setSelectedSize]   = useState(null)
  const [quantity, setQuantity]           = useState(1)
  const [mainImgIdx, setMainImgIdx]       = useState(0)  // index ảnh đang xem to

  // Trạng thái thêm giỏ
  const [addingCart, setAddingCart]   = useState(false)
  const [cartMsg, setCartMsg]         = useState(null)   // { type: 'success'|'error', text }

  // Fetch dữ liệu sản phẩm
  useEffect(() => {
    setLoading(true)
    getProductById(id)
      .then((res) => {
        const p = res.data
        setProduct(p)
        // Tự chọn màu đầu tiên khi load xong
        const colors = getUniqueColors(p.variants ?? [])
        if (colors.length) setSelectedColor(colors[0])
      })
      .catch(() => setError('Không tìm thấy sản phẩm'))
      .finally(() => setLoading(false))
  }, [id])

  // Reset size khi đổi màu
  useEffect(() => {
    setSelectedSize(null)
    setMainImgIdx(0)
    setQuantity(1)
  }, [selectedColor])

  // ── Dữ liệu tính toán từ lựa chọn hiện tại ──────────────────────────────────

  // Tất cả màu có trong variants
  const allColors = useMemo(
    () => getUniqueColors(product?.variants ?? []),
    [product]
  )

  // Ảnh của màu đang chọn, sắp xếp theo displayOrder
  const colorImages = useMemo(() => {
    if (!product?.images || !selectedColor) return []
    return [...product.images]
      .filter((img) => img.color === selectedColor)
      .sort((a, b) => a.displayOrder - b.displayOrder)
  }, [product, selectedColor])

  // Sizes của màu đang chọn (chỉ lấy size còn hàng)
  const availableSizes = useMemo(() => {
    if (!product?.variants || !selectedColor) return []
    return product.variants
      .filter((v) => v.color === selectedColor)
      .map((v) => ({ size: v.size, stock: v.stock, variantId: v.variantId, price: v.price }))
      .sort((a, b) => SIZE_ORDER.indexOf(a.size) - SIZE_ORDER.indexOf(b.size))
  }, [product, selectedColor])

  // Variant đang được chọn (màu + size)
  const selectedVariant = useMemo(() => {
    if (!selectedColor || !selectedSize) return null
    return availableSizes.find((v) => v.size === selectedSize) ?? null
  }, [availableSizes, selectedColor, selectedSize])

  // ── Xử lý thêm vào giỏ ────────────────────────────────────────────────────
  const handleAddToCart = async () => {
    if (!isLoggedIn) { navigate('/login'); return }
    if (!selectedVariant) return

    setAddingCart(true)
    setCartMsg(null)
    try {
      await addToCart(selectedVariant.variantId, quantity)
      setCartMsg({ type: 'success', text: 'Đã thêm vào giỏ hàng!' })
    } catch (err) {
      setCartMsg({
        type: 'error',
        text: err.response?.data?.error || 'Thêm vào giỏ thất bại',
      })
    } finally {
      setAddingCart(false)
      setTimeout(() => setCartMsg(null), 3000)
    }
  }

  // ── Render ─────────────────────────────────────────────────────────────────
  if (loading) return <LoadingSpinner />
  if (error)   return <ErrorMessage message={error} />
  if (!product) return null

  const mainImg = colorImages[mainImgIdx]
  const imgSrc  = (url) => url ? normalizeImageUrl(url) : 'https://placehold.co/500x600?text=No+Image'

  const formatPrice = (p) =>
    p != null
      ? new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p)
      : null

  return (
    <div>
      {/* Breadcrumb */}
      <nav style={styles.breadcrumb}>
        <span style={styles.bcLink} onClick={() => navigate('/products')}>Sản phẩm</span>
        <span style={styles.bcSep}>/</span>
        <span style={styles.bcLink} onClick={() => navigate(`/products?category=${product.categoryName}`)}>
          {product.categoryName}
        </span>
        <span style={styles.bcSep}>/</span>
        <span style={styles.bcCurrent}>{product.name}</span>
      </nav>

      <div style={styles.layout}>
        {/* ── Cột trái: Gallery ảnh ── */}
        <div style={styles.gallery}>
          {/* Ảnh lớn */}
          <div style={styles.mainImgWrap}>
            <img
              src={imgSrc(mainImg?.imageUrl)}
              alt={product.name}
              style={styles.mainImg}
            />
          </div>

          {/* Thumbnails */}
          {colorImages.length > 1 && (
            <div style={styles.thumbRow}>
              {colorImages.map((img, i) => (
                <div
                  key={img.imageId}
                  onClick={() => setMainImgIdx(i)}
                  style={{
                    ...styles.thumb,
                    ...(i === mainImgIdx ? styles.thumbActive : {}),
                  }}
                >
                  <img src={imgSrc(img.imageUrl)} alt="" style={styles.thumbImg} />
                </div>
              ))}
            </div>
          )}
        </div>

        {/* ── Cột phải: Thông tin + chọn ── */}
        <div style={styles.info}>
          <p style={styles.brandTag}>{product.brandName}</p>
          <h1 style={styles.productName}>{product.name}</h1>

          {/* Giá — hiện khi chọn size, nếu chưa chọn hiện giá thấp nhất */}
          <p style={styles.price}>
            {selectedVariant
              ? formatPrice(selectedVariant.price)
              : formatPrice(Math.min(...(product.variants ?? []).map((v) => v.price)))}
          </p>

          {product.description && (
            <p style={styles.description}>{product.description}</p>
          )}

          {/* ── Chọn màu ── */}
          <div style={styles.section}>
            <p style={styles.sectionLabel}>
              Màu sắc: <strong>{selectedColor}</strong>
            </p>
            <div style={styles.colorRow}>
              {allColors.map((color) => (
                <button
                  key={color}
                  onClick={() => setSelectedColor(color)}
                  title={color}
                  style={{
                    ...styles.colorBtn,
                    ...(selectedColor === color ? styles.colorBtnActive : {}),
                  }}
                >
                  {color}
                </button>
              ))}
            </div>
          </div>

          {/* ── Chọn size ── */}
          <div style={styles.section}>
            <p style={styles.sectionLabel}>Kích thước:</p>
            <div style={styles.sizeRow}>
              {availableSizes.map(({ size, stock, variantId }) => {
                const outOfStock = stock === 0
                return (
                  <button
                    key={variantId}
                    onClick={() => !outOfStock && setSelectedSize(size)}
                    disabled={outOfStock}
                    title={outOfStock ? 'Hết hàng' : `Còn ${stock} sản phẩm`}
                    style={{
                      ...styles.sizeBtn,
                      ...(selectedSize === size ? styles.sizeBtnActive : {}),
                      ...(outOfStock ? styles.sizeBtnDisabled : {}),
                    }}
                  >
                    {size}
                    {outOfStock && <span style={styles.outBadge}>Hết</span>}
                  </button>
                )
              })}
            </div>
          </div>

          {/* Tồn kho */}
          {selectedVariant && (
            <p style={styles.stockInfo}>
              {selectedVariant.stock > 0
                ? `Còn ${selectedVariant.stock} sản phẩm`
                : 'Hết hàng'}
            </p>
          )}

          {/* ── Số lượng ── */}
          <div style={styles.section}>
            <p style={styles.sectionLabel}>Số lượng:</p>
            <div style={styles.qtyRow}>
              <button
                onClick={() => setQuantity((q) => Math.max(1, q - 1))}
                style={styles.qtyBtn}
              >−</button>
              <span style={styles.qtyVal}>{quantity}</span>
              <button
                onClick={() => setQuantity((q) => Math.min(selectedVariant?.stock ?? 10, q + 1))}
                style={styles.qtyBtn}
              >+</button>
            </div>
          </div>

          {/* ── Thêm vào giỏ ── */}
          {cartMsg && (
            <div style={{
              ...styles.cartMsg,
              ...(cartMsg.type === 'success' ? styles.cartMsgOk : styles.cartMsgErr),
            }}>
              {cartMsg.text}
            </div>
          )}

          <button
            onClick={handleAddToCart}
            disabled={!selectedVariant || selectedVariant.stock === 0 || addingCart}
            style={{
              ...styles.addBtn,
              ...(!selectedVariant || selectedVariant.stock === 0 ? styles.addBtnDisabled : {}),
            }}
          >
            {addingCart
              ? 'Đang thêm...'
              : !selectedSize
                ? 'Vui lòng chọn size'
                : selectedVariant?.stock === 0
                  ? 'Hết hàng'
                  : 'Thêm vào giỏ hàng'}
          </button>

          {/* Thông tin thêm */}
          <div style={styles.meta}>
            <span>🏷 Danh mục: {product.categoryName}</span>
            <span>🏭 Thương hiệu: {product.brandName}</span>
          </div>
        </div>
      </div>
    </div>
  )
}

// ─── Helpers ──────────────────────────────────────────────────────────────────
const SIZE_ORDER = ['XS', 'S', 'M', 'L', 'XL', 'XXL']

function getUniqueColors(variants) {
  const seen = new Set()
  return variants
    .map((v) => v.color)
    .filter((c) => { if (seen.has(c)) return false; seen.add(c); return true })
}

function normalizeImageUrl(url) {
  if (url.startsWith('http') || url.startsWith('/')) return url
  return `/${url}`
}

// ─── Styles ───────────────────────────────────────────────────────────────────
const styles = {
  breadcrumb: {
    display: 'flex', gap: '6px', alignItems: 'center',
    marginBottom: '24px', fontSize: '0.85rem',
  },
  bcLink:    { color: '#6b7280', cursor: 'pointer' },
  bcSep:     { color: '#d1d5db' },
  bcCurrent: { color: '#111827', fontWeight: 500 },

  layout: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: '48px',
    alignItems: 'flex-start',
  },

  // Gallery
  gallery:     { position: 'sticky', top: '80px' },
  mainImgWrap: {
    aspectRatio: '3/4', borderRadius: '12px',
    overflow: 'hidden', backgroundColor: '#f3f4f6', marginBottom: '12px',
  },
  mainImg: { width: '100%', height: '100%', objectFit: 'cover' },
  thumbRow: { display: 'flex', gap: '8px', flexWrap: 'wrap' },
  thumb: {
    width: '64px', height: '80px', borderRadius: '6px', overflow: 'hidden',
    border: '2px solid transparent', cursor: 'pointer', flexShrink: 0,
  },
  thumbActive: { borderColor: '#111827' },
  thumbImg:    { width: '100%', height: '100%', objectFit: 'cover' },

  // Info
  info:        { display: 'flex', flexDirection: 'column', gap: '4px' },
  brandTag:    { fontSize: '0.8rem', color: '#9ca3af', textTransform: 'uppercase', letterSpacing: '0.05em' },
  productName: { fontSize: '1.4rem', fontWeight: 700, lineHeight: 1.3, marginBottom: '4px' },
  price:       { fontSize: '1.5rem', fontWeight: 800, color: '#dc2626', marginBottom: '8px' },
  description: { fontSize: '0.875rem', color: '#6b7280', lineHeight: 1.6, marginBottom: '8px' },

  section:      { marginTop: '16px' },
  sectionLabel: { fontSize: '0.875rem', color: '#374151', marginBottom: '8px' },

  // Màu
  colorRow: { display: 'flex', flexWrap: 'wrap', gap: '8px' },
  colorBtn: {
    padding: '6px 14px', borderRadius: '6px',
    border: '1.5px solid #e5e7eb', background: '#fff',
    fontSize: '0.8rem', cursor: 'pointer', color: '#374151',
    transition: 'all 0.15s',
  },
  colorBtnActive: { borderColor: '#111827', background: '#111827', color: '#fff' },

  // Size
  sizeRow: { display: 'flex', flexWrap: 'wrap', gap: '8px' },
  sizeBtn: {
    width: '52px', height: '44px', borderRadius: '8px',
    border: '1.5px solid #e5e7eb', background: '#fff',
    fontSize: '0.875rem', fontWeight: 600, cursor: 'pointer',
    display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center',
    position: 'relative', transition: 'all 0.15s',
  },
  sizeBtnActive:   { borderColor: '#111827', background: '#111827', color: '#fff' },
  sizeBtnDisabled: { opacity: 0.4, cursor: 'not-allowed', background: '#f9fafb' },
  outBadge: { fontSize: '0.55rem', color: '#ef4444' },

  stockInfo: { fontSize: '0.8rem', color: '#6b7280', marginTop: '8px' },

  // Số lượng
  qtyRow: { display: 'flex', alignItems: 'center', gap: '12px' },
  qtyBtn: {
    width: '36px', height: '36px', borderRadius: '8px',
    border: '1px solid #d1d5db', background: '#fff',
    fontSize: '1.2rem', cursor: 'pointer', display: 'flex',
    alignItems: 'center', justifyContent: 'center',
  },
  qtyVal: { fontSize: '1rem', fontWeight: 600, minWidth: '24px', textAlign: 'center' },

  // Cart
  cartMsg:    { padding: '10px 14px', borderRadius: '8px', fontSize: '0.875rem', marginTop: '12px' },
  cartMsgOk:  { background: '#f0fdf4', color: '#16a34a', border: '1px solid #bbf7d0' },
  cartMsgErr: { background: '#fef2f2', color: '#dc2626', border: '1px solid #fecaca' },
  addBtn: {
    marginTop: '16px', padding: '14px', width: '100%',
    background: '#111827', color: '#fff', border: 'none',
    borderRadius: '10px', fontSize: '1rem', fontWeight: 700,
    cursor: 'pointer', transition: 'opacity 0.15s',
  },
  addBtnDisabled: { background: '#9ca3af', cursor: 'not-allowed' },

  meta: {
    display: 'flex', flexDirection: 'column', gap: '4px',
    marginTop: '20px', fontSize: '0.8rem', color: '#9ca3af',
  },
}
