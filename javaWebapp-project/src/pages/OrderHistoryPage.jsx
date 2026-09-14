import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { cancelOrder, getMyOrders, getOrderDetails } from '../api/orderApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../context/AuthContext'

// ─── Status config ────────────────────────────────────────────────────────────
const STATUS = {
  PENDING:    { label: 'Chờ xác nhận', color: '#f59e0b', bg: '#fffbeb' },
  PROCESSING: { label: 'Đang xử lý',   color: '#3b82f6', bg: '#eff6ff' },
  SHIPPED:    { label: 'Đang giao',     color: '#8b5cf6', bg: '#f5f3ff' },
  DELIVERED:  { label: 'Đã giao',       color: '#16a34a', bg: '#f0fdf4' },
  CANCELLED:  { label: 'Đã hủy',        color: '#6b7280', bg: '#f9fafb' },
}

const fmt = (p) =>
  new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p ?? 0)

const fmtDate = (dt) =>
  dt ? new Date(dt).toLocaleDateString('vi-VN', {
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit',
  }) : ''

// ─── Trang lịch sử đơn hàng ──────────────────────────────────────────────────
// Route /orders          → danh sách đơn
// Route /orders/:orderId → chi tiết một đơn (dùng chung component này)

export default function OrderHistoryPage() {
  const { orderId }    = useParams()   // có khi vào /orders/:orderId
  const { isLoggedIn } = useAuth()
  const navigate       = useNavigate()

  const [orders, setOrders]   = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError]     = useState(null)

  // Đơn đang mở xem chi tiết — nếu URL có :orderId thì mở ngay
  const [openId, setOpenId]   = useState(orderId ? Number(orderId) : null)
  const [details, setDetails] = useState({})     // { orderId: [...items] }
  const [loadingDetail, setLoadingDetail] = useState(null)
  const [cancelling, setCancelling]       = useState(null)

  useEffect(() => {
    if (!isLoggedIn) { navigate('/login'); return }
    getMyOrders()
      .then((res) => setOrders(res.data))
      .catch(() => setError('Không thể tải đơn hàng'))
      .finally(() => setLoading(false))
  }, [isLoggedIn])

  // Khi URL có :orderId, tự load detail ngay
  useEffect(() => {
    if (orderId) loadDetail(Number(orderId))
  }, [orderId])

  const loadDetail = async (id) => {
    if (details[id]) return    // đã load rồi, không gọi lại
    setLoadingDetail(id)
    try {
      const res = await getOrderDetails(id)
      setDetails((prev) => ({ ...prev, [id]: res.data }))
    } catch {
      setDetails((prev) => ({ ...prev, [id]: [] }))
    } finally {
      setLoadingDetail(null)
    }
  }

  const toggleDetail = (id) => {
    if (openId === id) {
      setOpenId(null)
    } else {
      setOpenId(id)
      loadDetail(id)
    }
  }

  const handleCancel = async (id) => {
    if (!window.confirm('Bạn chắc muốn hủy đơn hàng này?')) return
    setCancelling(id)
    try {
      await cancelOrder(id)
      setOrders((prev) =>
        prev.map((o) => (o.orderId === id ? { ...o, status: 'CANCELLED' } : o))
      )
    } catch (err) {
      alert(err.response?.data?.error || 'Hủy đơn thất bại')
    } finally {
      setCancelling(null)
    }
  }

  // ── Render ─────────────────────────────────────────────────────────────────
  if (loading) return <LoadingSpinner />
  if (error)   return <ErrorMessage message={error} />

  return (
    <div>
      <h1 style={st.title}>Đơn hàng của tôi</h1>

      {orders.length === 0 ? (
        <div style={st.empty}>
          <div style={st.emptyIcon}>📦</div>
          <p style={st.emptyText}>Bạn chưa có đơn hàng nào</p>
          <button onClick={() => navigate('/products')} style={st.shopBtn}>
            Mua sắm ngay
          </button>
        </div>
      ) : (
        <div style={st.list}>
          {orders.map((order) => {
            const status  = STATUS[order.status] ?? STATUS.PENDING
            const isOpen  = openId === order.orderId
            const canCancel = order.status === 'PENDING' || order.status === 'PROCESSING'

            return (
              <div key={order.orderId} style={st.card}>
                {/* ── Header của card ── */}
                <div style={st.cardHeader}>
                  <div style={st.orderMeta}>
                    <span style={st.orderId}>Đơn #{order.orderId}</span>
                    <span style={st.orderDate}>{fmtDate(order.createdAt)}</span>
                  </div>

                  <div style={st.rightMeta}>
                    {/* Badge trạng thái */}
                    <span style={{ ...st.statusBadge, color: status.color, background: status.bg }}>
                      {status.label}
                    </span>
                    <span style={st.totalAmt}>{fmt(order.totalAmount)}</span>
                  </div>
                </div>

                {/* Địa chỉ giao hàng */}
                {order.shippingAddress && (
                  <p style={st.addrLine}>🏠 {order.shippingAddress}</p>
                )}

                {/* ── Actions ── */}
                <div style={st.actions}>
                  <button
                    onClick={() => toggleDetail(order.orderId)}
                    style={st.detailBtn}
                  >
                    {isOpen ? '▲ Ẩn chi tiết' : '▼ Xem chi tiết'}
                  </button>

                  {canCancel && (
                    <button
                      onClick={() => handleCancel(order.orderId)}
                      disabled={cancelling === order.orderId}
                      style={st.cancelBtn}
                    >
                      {cancelling === order.orderId ? 'Đang hủy...' : 'Hủy đơn'}
                    </button>
                  )}
                </div>

                {/* ── Chi tiết sản phẩm (collapsible) ── */}
                {isOpen && (
                  <div style={st.detailWrap}>
                    {loadingDetail === order.orderId ? (
                      <p style={st.detailLoading}>Đang tải...</p>
                    ) : (details[order.orderId] ?? []).length === 0 ? (
                      <p style={st.detailLoading}>Không có dữ liệu</p>
                    ) : (
                      <>
                        {(details[order.orderId] ?? []).map((d) => (
                          <div key={d.orderDetailId} style={st.detailRow}>
                            <div style={st.detailInfo}>
                              <p style={st.detailName}>{d.productName ?? d.variantSku}</p>
                              <p style={st.detailMeta}>
                                {d.color} / {d.size} × {d.quantity}
                              </p>
                            </div>
                            <div style={st.detailRight}>
                              <p style={st.detailPrice}>{fmt(d.price)}</p>
                              <p style={st.detailSub}>{fmt(d.subtotal)}</p>
                            </div>
                          </div>
                        ))}

                        {/* Tóm tắt chi phí */}
                        <div style={st.costSummary}>
                          <div style={st.costRow}>
                            <span>Phí vận chuyển</span>
                            <span>{order.shippingFee === 0 ? 'Miễn phí' : fmt(order.shippingFee)}</span>
                          </div>
                          <div style={{ ...st.costRow, ...st.costTotal }}>
                            <span>Tổng cộng</span>
                            <span style={{ color: '#dc2626' }}>{fmt(order.totalAmount)}</span>
                          </div>
                        </div>
                      </>
                    )}
                  </div>
                )}
              </div>
            )
          })}
        </div>
      )}
    </div>
  )
}

// ─── Styles ───────────────────────────────────────────────────────────────────
const st = {
  title: { fontSize: '1.5rem', fontWeight: 700, marginBottom: '24px' },

  empty:     { textAlign: 'center', padding: '80px 20px' },
  emptyIcon: { fontSize: '4rem', marginBottom: '16px' },
  emptyText: { color: '#6b7280', marginBottom: '24px', fontSize: '1.1rem' },
  shopBtn: {
    padding: '12px 28px', background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px', cursor: 'pointer',
    fontSize: '0.95rem', fontWeight: 600,
  },

  list: { display: 'flex', flexDirection: 'column', gap: '16px' },

  card: {
    background: '#fff', borderRadius: '14px',
    border: '1px solid #e5e7eb', padding: '20px 24px',
  },
  cardHeader: {
    display: 'flex', justifyContent: 'space-between',
    alignItems: 'flex-start', marginBottom: '8px',
  },
  orderMeta: { display: 'flex', flexDirection: 'column', gap: '4px' },
  orderId:   { fontWeight: 700, fontSize: '0.95rem' },
  orderDate: { fontSize: '0.8rem', color: '#9ca3af' },
  rightMeta: { display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '6px' },
  statusBadge: {
    fontSize: '0.75rem', fontWeight: 600, padding: '3px 10px',
    borderRadius: '20px', display: 'inline-block',
  },
  totalAmt: { fontWeight: 700, fontSize: '1rem' },
  addrLine: { fontSize: '0.8rem', color: '#6b7280', marginBottom: '12px' },

  actions: { display: 'flex', gap: '10px', marginTop: '4px' },
  detailBtn: {
    background: 'none', border: '1px solid #e5e7eb', borderRadius: '7px',
    padding: '6px 14px', cursor: 'pointer', fontSize: '0.8rem', color: '#374151',
  },
  cancelBtn: {
    background: 'none', border: '1px solid #fca5a5', borderRadius: '7px',
    padding: '6px 14px', cursor: 'pointer', fontSize: '0.8rem', color: '#dc2626',
  },

  detailWrap: {
    marginTop: '16px', borderTop: '1px solid #f3f4f6', paddingTop: '16px',
  },
  detailLoading: { color: '#9ca3af', fontSize: '0.875rem', textAlign: 'center', padding: '12px' },
  detailRow: {
    display: 'flex', justifyContent: 'space-between',
    alignItems: 'flex-start', gap: '16px',
    padding: '10px 0', borderBottom: '1px solid #f9fafb',
  },
  detailInfo:  { flex: 1, minWidth: 0 },
  detailName:  { fontWeight: 500, fontSize: '0.875rem', marginBottom: '3px' },
  detailMeta:  { fontSize: '0.78rem', color: '#9ca3af' },
  detailRight: { textAlign: 'right', flexShrink: 0 },
  detailPrice: { fontSize: '0.78rem', color: '#6b7280' },
  detailSub:   { fontWeight: 600, fontSize: '0.875rem', marginTop: '2px' },

  costSummary: {
    marginTop: '12px', padding: '12px 0 0',
    display: 'flex', flexDirection: 'column', gap: '6px',
  },
  costRow: {
    display: 'flex', justifyContent: 'space-between',
    fontSize: '0.875rem', color: '#374151',
  },
  costTotal: { fontWeight: 700, fontSize: '1rem', marginTop: '4px' },
}
