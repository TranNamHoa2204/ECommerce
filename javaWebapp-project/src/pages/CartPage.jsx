import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { clearCart, deleteCartItem, getMyCart, updateCartItem } from '../api/cartApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../context/AuthContext'

// ─── Trang giỏ hàng ───────────────────────────────────────────────────────────

export default function CartPage() {
  const { isLoggedIn } = useAuth()
  const navigate = useNavigate()

  const [items, setItems]     = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError]     = useState(null)
  const [updating, setUpdating] = useState(null) // cartItemId đang xử lý

  // Redirect về login nếu chưa đăng nhập
  useEffect(() => {
    if (!isLoggedIn) { navigate('/login'); return }
    loadCart()
  }, [isLoggedIn])

  const loadCart = () => {
    setLoading(true)
    getMyCart()
      .then((res) => setItems(res.data))
      .catch(() => setError('Không thể tải giỏ hàng'))
      .finally(() => setLoading(false))
  }

  // Cập nhật số lượng — gọi API rồi cập nhật local state (không reload lại toàn bộ)
  const handleQuantityChange = async (cartItemId, newQty) => {
    if (newQty < 1) return
    setUpdating(cartItemId)
    try {
      await updateCartItem(cartItemId, newQty)
      setItems((prev) =>
        prev.map((item) =>
          item.cartItemId === cartItemId
            ? { ...item, quantity: newQty, subtotal: item.price * newQty }
            : item
        )
      )
    } catch (err) {
      setError(err.response?.data?.error || 'Cập nhật thất bại')
    } finally {
      setUpdating(null)
    }
  }

  // Xóa một item
  const handleDelete = async (cartItemId) => {
    setUpdating(cartItemId)
    try {
      await deleteCartItem(cartItemId)
      setItems((prev) => prev.filter((item) => item.cartItemId !== cartItemId))
    } catch {
      setError('Xóa sản phẩm thất bại')
    } finally {
      setUpdating(null)
    }
  }

  // Xóa toàn bộ giỏ
  const handleClear = async () => {
    if (!window.confirm('Bạn muốn xóa toàn bộ giỏ hàng?')) return
    try {
      await clearCart()
      setItems([])
    } catch {
      setError('Xóa giỏ hàng thất bại')
    }
  }

  // Tính tổng tiền
  const totalAmount = items.reduce((sum, item) => sum + Number(item.subtotal ?? 0), 0)
  const shippingFee = totalAmount >= 500000 ? 0 : 30000  // miễn phí ship từ 500k

  const formatPrice = (p) =>
    new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p)

  if (loading) return <LoadingSpinner />

  return (
    <div>
      <h1 style={styles.title}>Giỏ hàng</h1>

      {error && <ErrorMessage message={error} />}

      {items.length === 0 ? (
        // Giỏ trống
        <div style={styles.emptyWrap}>
          <div style={styles.emptyIcon}>🛒</div>
          <p style={styles.emptyText}>Giỏ hàng của bạn đang trống</p>
          <button onClick={() => navigate('/products')} style={styles.shopBtn}>
            Tiếp tục mua sắm
          </button>
        </div>
      ) : (
        <div style={styles.layout}>
          {/* ── Danh sách sản phẩm ── */}
          <div style={styles.itemList}>
            <div style={styles.listHeader}>
              <span>Sản phẩm ({items.length} sản phẩm)</span>
              <button onClick={handleClear} style={styles.clearBtn}>
                Xóa tất cả
              </button>
            </div>

            {items.map((item) => (
              <div key={item.cartItemId} style={styles.itemCard}>
                {/* Ảnh placeholder — sẽ cải thiện sau khi có image URL trong CartItem */}
                <div style={styles.itemImg}>
                  <div style={styles.imgPlaceholder}>👕</div>
                </div>

                <div style={styles.itemInfo}>
                  <p style={styles.itemName}>{item.productName}</p>
                  <p style={styles.itemVariant}>
                    Màu: {item.color} &nbsp;|&nbsp; Size: {item.size}
                  </p>
                  <p style={styles.itemPrice}>{formatPrice(item.price)}</p>
                </div>

                {/* Điều chỉnh số lượng */}
                <div style={styles.qtyControl}>
                  <button
                    onClick={() => handleQuantityChange(item.cartItemId, item.quantity - 1)}
                    disabled={updating === item.cartItemId || item.quantity <= 1}
                    style={styles.qtyBtn}
                  >−</button>
                  <span style={styles.qtyVal}>
                    {updating === item.cartItemId ? '…' : item.quantity}
                  </span>
                  <button
                    onClick={() => handleQuantityChange(item.cartItemId, item.quantity + 1)}
                    disabled={updating === item.cartItemId}
                    style={styles.qtyBtn}
                  >+</button>
                </div>

                {/* Thành tiền */}
                <div style={styles.itemSubtotal}>
                  <p style={styles.subtotalVal}>{formatPrice(item.subtotal)}</p>
                  <button
                    onClick={() => handleDelete(item.cartItemId)}
                    disabled={updating === item.cartItemId}
                    style={styles.deleteBtn}
                    title="Xóa sản phẩm"
                  >✕</button>
                </div>
              </div>
            ))}
          </div>

          {/* ── Tóm tắt đơn hàng ── */}
          <div style={styles.summary}>
            <h2 style={styles.summaryTitle}>Tóm tắt đơn hàng</h2>

            <div style={styles.summaryRow}>
              <span>Tạm tính</span>
              <span>{formatPrice(totalAmount)}</span>
            </div>
            <div style={styles.summaryRow}>
              <span>Phí vận chuyển</span>
              <span style={shippingFee === 0 ? { color: '#16a34a' } : {}}>
                {shippingFee === 0 ? 'Miễn phí' : formatPrice(shippingFee)}
              </span>
            </div>
            {shippingFee > 0 && (
              <p style={styles.shippingNote}>
                Mua thêm {formatPrice(500000 - totalAmount)} để được miễn phí ship
              </p>
            )}

            <div style={styles.summaryDivider} />

            <div style={{ ...styles.summaryRow, ...styles.summaryTotal }}>
              <span>Tổng cộng</span>
              <span>{formatPrice(totalAmount + shippingFee)}</span>
            </div>

            <button
              onClick={() => navigate('/checkout')}
              style={styles.checkoutBtn}
            >
              Tiến hành đặt hàng →
            </button>

            <button
              onClick={() => navigate('/products')}
              style={styles.continueBtn}
            >
              Tiếp tục mua sắm
            </button>
          </div>
        </div>
      )}
    </div>
  )
}

// ─── Styles ───────────────────────────────────────────────────────────────────
const styles = {
  title: { fontSize: '1.5rem', fontWeight: 700, marginBottom: '24px' },

  emptyWrap: { textAlign: 'center', padding: '80px 20px' },
  emptyIcon: { fontSize: '4rem', marginBottom: '16px' },
  emptyText: { color: '#6b7280', marginBottom: '24px', fontSize: '1.1rem' },
  shopBtn: {
    padding: '12px 28px', background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px', cursor: 'pointer',
    fontSize: '0.95rem', fontWeight: 600,
  },

  layout: {
    display: 'grid',
    gridTemplateColumns: '1fr 340px',
    gap: '32px',
    alignItems: 'flex-start',
  },

  // List
  itemList: { display: 'flex', flexDirection: 'column', gap: '12px' },
  listHeader: {
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
    padding: '0 4px', marginBottom: '4px',
    fontSize: '0.875rem', color: '#374151',
  },
  clearBtn: {
    background: 'none', border: 'none', cursor: 'pointer',
    color: '#ef4444', fontSize: '0.8rem',
  },

  itemCard: {
    display: 'grid',
    gridTemplateColumns: '72px 1fr auto auto',
    gap: '16px', alignItems: 'center',
    background: '#fff', borderRadius: '12px',
    border: '1px solid #e5e7eb', padding: '16px',
  },
  itemImg: {
    width: '72px', height: '90px', borderRadius: '8px',
    overflow: 'hidden', backgroundColor: '#f3f4f6',
    display: 'flex', alignItems: 'center', justifyContent: 'center',
  },
  imgPlaceholder: { fontSize: '2rem' },
  itemInfo: { minWidth: 0 },
  itemName: {
    fontWeight: 600, fontSize: '0.9rem', marginBottom: '4px',
    whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis',
  },
  itemVariant: { fontSize: '0.78rem', color: '#9ca3af', marginBottom: '4px' },
  itemPrice:   { fontSize: '0.875rem', color: '#374151' },

  qtyControl:  { display: 'flex', alignItems: 'center', gap: '8px' },
  qtyBtn: {
    width: '30px', height: '30px', borderRadius: '6px',
    border: '1px solid #d1d5db', background: '#fff',
    cursor: 'pointer', fontSize: '1rem',
    display: 'flex', alignItems: 'center', justifyContent: 'center',
  },
  qtyVal: { minWidth: '20px', textAlign: 'center', fontWeight: 600 },

  itemSubtotal: { display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '8px' },
  subtotalVal:  { fontWeight: 700, fontSize: '0.9rem', whiteSpace: 'nowrap' },
  deleteBtn: {
    background: 'none', border: 'none', cursor: 'pointer',
    color: '#9ca3af', fontSize: '0.9rem', padding: '2px 4px',
  },

  // Summary
  summary: {
    background: '#fff', borderRadius: '14px',
    border: '1px solid #e5e7eb', padding: '24px',
    position: 'sticky', top: '80px',
  },
  summaryTitle: { fontSize: '1.1rem', fontWeight: 700, marginBottom: '20px' },
  summaryRow: {
    display: 'flex', justifyContent: 'space-between',
    fontSize: '0.875rem', color: '#374151', marginBottom: '12px',
  },
  shippingNote: { fontSize: '0.75rem', color: '#f59e0b', marginBottom: '12px' },
  summaryDivider: { borderTop: '1px solid #e5e7eb', margin: '12px 0' },
  summaryTotal: { fontWeight: 700, fontSize: '1rem', color: '#111827' },

  checkoutBtn: {
    display: 'block', width: '100%', padding: '14px',
    background: '#111827', color: '#fff', border: 'none',
    borderRadius: '10px', fontSize: '1rem', fontWeight: 700,
    cursor: 'pointer', marginTop: '16px', textAlign: 'center',
  },
  continueBtn: {
    display: 'block', width: '100%', padding: '10px',
    background: 'none', color: '#374151',
    border: '1px solid #d1d5db', borderRadius: '10px',
    fontSize: '0.875rem', cursor: 'pointer',
    marginTop: '8px', textAlign: 'center',
  },
}
