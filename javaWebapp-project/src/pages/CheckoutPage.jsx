import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getAddresses } from '../api/addressApi'
import { getMyCart } from '../api/cartApi'
import { createOrder } from '../api/orderApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../context/AuthContext'

const PAYMENT_METHODS = [
  { value: 'COD',           label: 'Thanh toán khi nhận hàng (COD)',   icon: '🚚' },
  { value: 'BANK_TRANSFER', label: 'Chuyển khoản ngân hàng',            icon: '🏦' },
  { value: 'VNPAY',         label: 'Ví điện tử VNPay',                  icon: '💳' },
]

export default function CheckoutPage() {
  const { user, isLoggedIn } = useAuth()
  const navigate = useNavigate()

  const [cartItems, setCartItems]       = useState([])
  const [addresses, setAddresses]       = useState([])
  const [loading, setLoading]           = useState(true)
  const [error, setError]               = useState(null)

  // Form state
  const [selectedAddr, setSelectedAddr] = useState(null)   // addressId
  const [paymentMethod, setPaymentMethod] = useState('COD')
  const [note, setNote]                 = useState('')
  const [placing, setPlacing]           = useState(false)
  const [placeError, setPlaceError]     = useState('')

  useEffect(() => {
    if (!isLoggedIn) { navigate('/login'); return }

    // Load giỏ hàng và địa chỉ song song
    Promise.all([getMyCart(), getAddresses(user.userId)])
      .then(([cartRes, addrRes]) => {
        const items = cartRes.data
        if (items.length === 0) { navigate('/cart'); return }
        setCartItems(items)

        const addrs = addrRes.data
        setAddresses(addrs)
        // Tự chọn địa chỉ mặc định nếu có
        const def = addrs.find((a) => a.isDefault)
        if (def) setSelectedAddr(def.addressId)
        else if (addrs.length === 1) setSelectedAddr(addrs[0].addressId)
      })
      .catch(() => setError('Không thể tải dữ liệu. Vui lòng thử lại.'))
      .finally(() => setLoading(false))
  }, [isLoggedIn])

  // ── Tính toán ──────────────────────────────────────────────────────────────
  const subtotal    = cartItems.reduce((s, i) => s + Number(i.subtotal ?? 0), 0)
  const shippingFee = subtotal >= 500000 ? 0 : 30000
  const total       = subtotal + shippingFee

  const fmt = (p) =>
    new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(p)

  // ── Đặt hàng ──────────────────────────────────────────────────────────────
  const handlePlaceOrder = async () => {
    setPlaceError('')

    if (!selectedAddr) {
      setPlaceError('Vui lòng chọn địa chỉ giao hàng')
      return
    }

    setPlacing(true)
    try {
      const body = {
        addressId:     selectedAddr,
        shippingFee:   shippingFee,
        note:          note.trim() || null,
        paymentMethod: paymentMethod,
        items: cartItems.map((item) => ({
          variantId: item.variantId,
          quantity:  item.quantity,
          price:     item.price,
        })),
      }

      const res = await createOrder(body)
      const orderId = res.data.orderId
      navigate(`/orders/${orderId}`)       // chuyển sang trang chi tiết đơn vừa đặt
    } catch (err) {
      setPlaceError(err.response?.data?.error || 'Đặt hàng thất bại, vui lòng thử lại')
    } finally {
      setPlacing(false)
    }
  }

  // ── Render ─────────────────────────────────────────────────────────────────
  if (loading) return <LoadingSpinner />
  if (error)   return <ErrorMessage message={error} />

  return (
    <div>
      {/* Breadcrumb */}
      <nav style={s.breadcrumb}>
        <span style={s.bcLink} onClick={() => navigate('/cart')}>Giỏ hàng</span>
        <span style={s.bcSep}>/</span>
        <span style={s.bcCurrent}>Đặt hàng</span>
      </nav>

      <h1 style={s.title}>Xác nhận đơn hàng</h1>

      <div style={s.layout}>
        {/* ── Cột trái ── */}
        <div style={s.left}>

          {/* 1. Địa chỉ giao hàng */}
          <Section title="1. Địa chỉ giao hàng">
            {addresses.length === 0 ? (
              <div style={s.noAddr}>
                <p style={{ color: '#6b7280', marginBottom: '12px' }}>
                  Bạn chưa có địa chỉ nào.
                </p>
                <button
                  onClick={() => navigate('/profile')}
                  style={s.addAddrBtn}
                >
                  + Thêm địa chỉ trong trang tài khoản
                </button>
              </div>
            ) : (
              <div style={s.addrList}>
                {addresses.map((addr) => (
                  <label key={addr.addressId} style={{
                    ...s.addrCard,
                    ...(selectedAddr === addr.addressId ? s.addrCardSelected : {}),
                  }}>
                    <input
                      type="radio"
                      name="address"
                      value={addr.addressId}
                      checked={selectedAddr === addr.addressId}
                      onChange={() => setSelectedAddr(addr.addressId)}
                      style={{ marginTop: '3px', flexShrink: 0 }}
                    />
                    <div>
                      <p style={s.addrName}>
                        {addr.receiverName} — {addr.phone}
                        {addr.isDefault && <span style={s.defaultBadge}>Mặc định</span>}
                      </p>
                      <p style={s.addrText}>
                        {addr.detailAddress}, {addr.ward},<br />
                        {addr.district}, {addr.province}
                      </p>
                    </div>
                  </label>
                ))}
              </div>
            )}
          </Section>

          {/* 2. Phương thức thanh toán */}
          <Section title="2. Phương thức thanh toán">
            <div style={s.pmList}>
              {PAYMENT_METHODS.map((m) => (
                <label key={m.value} style={{
                  ...s.pmCard,
                  ...(paymentMethod === m.value ? s.pmCardSelected : {}),
                }}>
                  <input
                    type="radio"
                    name="payment"
                    value={m.value}
                    checked={paymentMethod === m.value}
                    onChange={() => setPaymentMethod(m.value)}
                  />
                  <span style={s.pmIcon}>{m.icon}</span>
                  <span style={s.pmLabel}>{m.label}</span>
                </label>
              ))}
            </div>
          </Section>

          {/* 3. Ghi chú */}
          <Section title="3. Ghi chú (không bắt buộc)">
            <textarea
              value={note}
              onChange={(e) => setNote(e.target.value)}
              placeholder="Ghi chú cho đơn hàng: giao giờ hành chính, để ở cổng..."
              rows={3}
              style={s.noteInput}
            />
          </Section>
        </div>

        {/* ── Cột phải: Tóm tắt ── */}
        <div style={s.summary}>
          <h2 style={s.summaryTitle}>Tóm tắt đơn hàng</h2>

          {/* Danh sách sản phẩm */}
          <div style={s.itemList}>
            {cartItems.map((item) => (
              <div key={item.cartItemId} style={s.itemRow}>
                <div style={s.itemLeft}>
                  <p style={s.itemName}>{item.productName}</p>
                  <p style={s.itemMeta}>{item.color} / {item.size} × {item.quantity}</p>
                </div>
                <span style={s.itemPrice}>{fmt(item.subtotal)}</span>
              </div>
            ))}
          </div>

          <div style={s.divider} />

          <div style={s.summaryRow}>
            <span>Tạm tính</span>
            <span>{fmt(subtotal)}</span>
          </div>
          <div style={s.summaryRow}>
            <span>Phí vận chuyển</span>
            <span style={shippingFee === 0 ? { color: '#16a34a' } : {}}>
              {shippingFee === 0 ? 'Miễn phí' : fmt(shippingFee)}
            </span>
          </div>

          <div style={s.divider} />

          <div style={{ ...s.summaryRow, ...s.totalRow }}>
            <span>Tổng cộng</span>
            <span style={{ color: '#dc2626' }}>{fmt(total)}</span>
          </div>

          {/* Lỗi đặt hàng */}
          {placeError && (
            <div style={s.errorBox}>{placeError}</div>
          )}

          <button
            onClick={handlePlaceOrder}
            disabled={placing || addresses.length === 0}
            style={{
              ...s.placeBtn,
              ...(placing || addresses.length === 0 ? s.placeBtnDisabled : {}),
            }}
          >
            {placing ? 'Đang đặt hàng...' : '✓ Đặt hàng ngay'}
          </button>

          <button onClick={() => navigate('/cart')} style={s.backBtn}>
            ← Quay lại giỏ hàng
          </button>
        </div>
      </div>
    </div>
  )
}

function Section({ title, children }) {
  return (
    <div style={s.section}>
      <h2 style={s.sectionTitle}>{title}</h2>
      {children}
    </div>
  )
}

// ─── Styles ───────────────────────────────────────────────────────────────────
const s = {
  breadcrumb: { display: 'flex', gap: '6px', alignItems: 'center', marginBottom: '20px', fontSize: '0.85rem' },
  bcLink:     { color: '#6b7280', cursor: 'pointer' },
  bcSep:      { color: '#d1d5db' },
  bcCurrent:  { color: '#111827', fontWeight: 500 },
  title:      { fontSize: '1.5rem', fontWeight: 700, marginBottom: '28px' },

  layout: { display: 'grid', gridTemplateColumns: '1fr 360px', gap: '32px', alignItems: 'flex-start' },
  left:   { display: 'flex', flexDirection: 'column', gap: '20px' },

  section:      { background: '#fff', borderRadius: '12px', border: '1px solid #e5e7eb', padding: '24px' },
  sectionTitle: { fontSize: '1rem', fontWeight: 700, marginBottom: '16px', color: '#111827' },

  // Địa chỉ
  noAddr:     { textAlign: 'center', padding: '8px 0' },
  addAddrBtn: {
    padding: '8px 16px', background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px', cursor: 'pointer', fontSize: '0.875rem',
  },
  addrList: { display: 'flex', flexDirection: 'column', gap: '10px' },
  addrCard: {
    display: 'flex', gap: '12px', padding: '14px 16px',
    border: '1.5px solid #e5e7eb', borderRadius: '10px', cursor: 'pointer',
    transition: 'border-color 0.15s',
  },
  addrCardSelected: { borderColor: '#111827', background: '#f9fafb' },
  addrName: { fontWeight: 600, fontSize: '0.875rem', marginBottom: '4px', display: 'flex', alignItems: 'center', gap: '8px' },
  addrText: { fontSize: '0.8rem', color: '#6b7280', lineHeight: 1.5 },
  defaultBadge: {
    fontSize: '0.65rem', background: '#111827', color: '#fff',
    padding: '2px 7px', borderRadius: '20px',
  },

  // Thanh toán
  pmList: { display: 'flex', flexDirection: 'column', gap: '8px' },
  pmCard: {
    display: 'flex', alignItems: 'center', gap: '12px',
    padding: '12px 16px', border: '1.5px solid #e5e7eb',
    borderRadius: '10px', cursor: 'pointer', transition: 'border-color 0.15s',
  },
  pmCardSelected: { borderColor: '#111827', background: '#f9fafb' },
  pmIcon:  { fontSize: '1.2rem' },
  pmLabel: { fontSize: '0.875rem', fontWeight: 500 },

  // Ghi chú
  noteInput: {
    width: '100%', padding: '10px 14px', borderRadius: '8px',
    border: '1px solid #d1d5db', fontSize: '0.875rem',
    resize: 'vertical', outline: 'none', fontFamily: 'inherit',
  },

  // Summary
  summary: {
    background: '#fff', borderRadius: '14px', border: '1px solid #e5e7eb',
    padding: '24px', position: 'sticky', top: '80px',
  },
  summaryTitle: { fontSize: '1.05rem', fontWeight: 700, marginBottom: '16px' },
  itemList:     { display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '4px' },
  itemRow:      { display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: '12px' },
  itemLeft:     { flex: 1, minWidth: 0 },
  itemName:     { fontSize: '0.825rem', fontWeight: 500, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' },
  itemMeta:     { fontSize: '0.75rem', color: '#9ca3af', marginTop: '2px' },
  itemPrice:    { fontSize: '0.825rem', fontWeight: 600, whiteSpace: 'nowrap' },
  divider:      { borderTop: '1px solid #f3f4f6', margin: '14px 0' },
  summaryRow:   { display: 'flex', justifyContent: 'space-between', fontSize: '0.875rem', color: '#374151', marginBottom: '8px' },
  totalRow:     { fontWeight: 700, fontSize: '1rem', color: '#111827' },

  errorBox: {
    background: '#fef2f2', border: '1px solid #fecaca', color: '#dc2626',
    borderRadius: '8px', padding: '10px 14px', fontSize: '0.825rem', marginTop: '12px',
  },
  placeBtn: {
    display: 'block', width: '100%', marginTop: '16px', padding: '14px',
    background: '#111827', color: '#fff', border: 'none', borderRadius: '10px',
    fontSize: '1rem', fontWeight: 700, cursor: 'pointer',
  },
  placeBtnDisabled: { background: '#9ca3af', cursor: 'not-allowed' },
  backBtn: {
    display: 'block', width: '100%', marginTop: '8px', padding: '10px',
    background: 'none', color: '#6b7280', border: '1px solid #e5e7eb',
    borderRadius: '10px', fontSize: '0.875rem', cursor: 'pointer',
  },
}
