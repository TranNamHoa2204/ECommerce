import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import {
  addAddress,
  deleteAddress,
  getAddresses,
  setDefaultAddress,
} from '../api/addressApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../context/AuthContext'

// ─── Trang thông tin cá nhân ──────────────────────────────────────────────────
// Gồm 2 tab: Thông tin tài khoản | Địa chỉ giao hàng

const EMPTY_ADDRESS = {
  receiverName: '', phone: '', province: '',
  district: '', ward: '', detailAddress: '', isDefault: false,
}

export default function ProfilePage() {
  const { user, isLoggedIn } = useAuth()
  const navigate = useNavigate()
  const [tab, setTab] = useState('info')   // 'info' | 'address'

  // Redirect về login nếu chưa đăng nhập
  useEffect(() => {
    if (!isLoggedIn) navigate('/login')
  }, [isLoggedIn, navigate])

  if (!user) return null

  return (
    <div style={styles.page}>
      {/* ── Sidebar tab ── */}
      <aside style={styles.sidebar}>
        <div style={styles.avatar}>
          {/* Avatar tạm bằng chữ cái đầu tên */}
          <div style={styles.avatarCircle}>
            {user.fullName?.[0]?.toUpperCase() ?? 'U'}
          </div>
          <div>
            <p style={styles.avatarName}>{user.fullName}</p>
            <p style={styles.avatarEmail}>{user.email}</p>
          </div>
        </div>

        <nav style={styles.nav}>
          <button
            onClick={() => setTab('info')}
            style={{ ...styles.navBtn, ...(tab === 'info' ? styles.navBtnActive : {}) }}
          >
            👤 Thông tin tài khoản
          </button>
          <button
            onClick={() => setTab('address')}
            style={{ ...styles.navBtn, ...(tab === 'address' ? styles.navBtnActive : {}) }}
          >
            📍 Địa chỉ giao hàng
          </button>
        </nav>
      </aside>

      {/* ── Nội dung tab ── */}
      <main style={styles.content}>
        {tab === 'info'    && <InfoTab user={user} />}
        {tab === 'address' && <AddressTab userId={user.userId} />}
      </main>
    </div>
  )
}

// ─── Tab: Thông tin tài khoản ─────────────────────────────────────────────────
function InfoTab({ user }) {
  return (
    <div>
      <h2 style={styles.tabTitle}>Thông tin tài khoản</h2>
      <div style={styles.infoCard}>
        <Row label="Họ và tên"   value={user.fullName} />
        <Row label="Email"        value={user.email} />
        <Row label="Số điện thoại" value={user.phone ?? 'Chưa cập nhật'} />
        <Row label="Vai trò"      value={user.role === 'ADMIN' ? 'Quản trị viên' : 'Khách hàng'} />
      </div>
      <p style={styles.hint}>
        * Tính năng chỉnh sửa thông tin sẽ có trong phiên bản tiếp theo.
      </p>
    </div>
  )
}

function Row({ label, value }) {
  return (
    <div style={styles.infoRow}>
      <span style={styles.infoLabel}>{label}</span>
      <span style={styles.infoValue}>{value}</span>
    </div>
  )
}

// ─── Tab: Địa chỉ giao hàng ───────────────────────────────────────────────────
function AddressTab({ userId }) {
  const [addresses, setAddresses] = useState([])
  const [loading, setLoading]     = useState(true)
  const [error, setError]         = useState(null)
  const [showForm, setShowForm]   = useState(false)
  const [form, setForm]           = useState(EMPTY_ADDRESS)
  const [formErrors, setFormErrors] = useState({})
  const [saving, setSaving]       = useState(false)

  // Load địa chỉ khi tab mount
  useEffect(() => {
    loadAddresses()
  }, [userId])

  const loadAddresses = () => {
    setLoading(true)
    getAddresses(userId)
      .then((res) => setAddresses(res.data))
      .catch(() => setError('Không thể tải địa chỉ'))
      .finally(() => setLoading(false))
  }

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setForm((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }))
    if (formErrors[name]) setFormErrors((prev) => ({ ...prev, [name]: '' }))
  }

  const validate = () => {
    const e = {}
    if (!form.receiverName.trim()) e.receiverName = 'Vui lòng nhập tên người nhận'
    if (!/^0\d{9}$/.test(form.phone))     e.phone        = 'Số điện thoại không hợp lệ'
    if (!form.province.trim())    e.province     = 'Vui lòng nhập tỉnh/thành phố'
    if (!form.district.trim())    e.district     = 'Vui lòng nhập quận/huyện'
    if (!form.ward.trim())        e.ward         = 'Vui lòng nhập phường/xã'
    if (!form.detailAddress.trim()) e.detailAddress = 'Vui lòng nhập địa chỉ chi tiết'
    return e
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const errs = validate()
    if (Object.keys(errs).length) { setFormErrors(errs); return }

    setSaving(true)
    try {
      await addAddress(userId, form)
      setForm(EMPTY_ADDRESS)
      setShowForm(false)
      loadAddresses()   // reload danh sách
    } catch (err) {
      setError(err.response?.data?.error || 'Thêm địa chỉ thất bại')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (addressId) => {
    if (!window.confirm('Bạn chắc muốn xóa địa chỉ này?')) return
    try {
      await deleteAddress(addressId, userId)
      setAddresses((prev) => prev.filter((a) => a.addressId !== addressId))
    } catch {
      setError('Xóa địa chỉ thất bại')
    }
  }

  const handleSetDefault = async (addressId) => {
    try {
      await setDefaultAddress(addressId, userId)
      // Cập nhật local — đánh dấu lại isDefault
      setAddresses((prev) =>
        prev.map((a) => ({ ...a, isDefault: a.addressId === addressId }))
      )
    } catch {
      setError('Thiết lập mặc định thất bại')
    }
  }

  if (loading) return <LoadingSpinner />
  if (error)   return <ErrorMessage message={error} />

  return (
    <div>
      <div style={styles.addressHeader}>
        <h2 style={styles.tabTitle}>Địa chỉ giao hàng</h2>
        <button
          onClick={() => setShowForm((v) => !v)}
          style={styles.addBtn}
        >
          {showForm ? '✕ Hủy' : '+ Thêm địa chỉ mới'}
        </button>
      </div>

      {/* ── Form thêm địa chỉ ── */}
      {showForm && (
        <form onSubmit={handleSubmit} style={styles.addressForm} noValidate>
          <div style={styles.formGrid}>
            <AddressField label="Tên người nhận *" error={formErrors.receiverName}>
              <input name="receiverName" value={form.receiverName}
                onChange={handleChange} placeholder="Nguyễn Văn A"
                style={{ ...styles.input, ...(formErrors.receiverName ? styles.inputErr : {}) }} />
            </AddressField>

            <AddressField label="Số điện thoại *" error={formErrors.phone}>
              <input name="phone" value={form.phone}
                onChange={handleChange} placeholder="0912345678"
                style={{ ...styles.input, ...(formErrors.phone ? styles.inputErr : {}) }} />
            </AddressField>

            <AddressField label="Tỉnh / Thành phố *" error={formErrors.province}>
              <input name="province" value={form.province}
                onChange={handleChange} placeholder="TP. Hồ Chí Minh"
                style={{ ...styles.input, ...(formErrors.province ? styles.inputErr : {}) }} />
            </AddressField>

            <AddressField label="Quận / Huyện *" error={formErrors.district}>
              <input name="district" value={form.district}
                onChange={handleChange} placeholder="Quận 1"
                style={{ ...styles.input, ...(formErrors.district ? styles.inputErr : {}) }} />
            </AddressField>

            <AddressField label="Phường / Xã *" error={formErrors.ward}>
              <input name="ward" value={form.ward}
                onChange={handleChange} placeholder="Phường Bến Nghé"
                style={{ ...styles.input, ...(formErrors.ward ? styles.inputErr : {}) }} />
            </AddressField>
          </div>

          <AddressField label="Địa chỉ chi tiết *" error={formErrors.detailAddress}>
            <input name="detailAddress" value={form.detailAddress}
              onChange={handleChange} placeholder="Số nhà, tên đường..."
              style={{ ...styles.input, ...(formErrors.detailAddress ? styles.inputErr : {}), width: '100%' }} />
          </AddressField>

          <label style={styles.checkboxRow}>
            <input type="checkbox" name="isDefault"
              checked={form.isDefault} onChange={handleChange} />
            <span>Đặt làm địa chỉ mặc định</span>
          </label>

          <button type="submit" disabled={saving} style={styles.saveBtn}>
            {saving ? 'Đang lưu...' : 'Lưu địa chỉ'}
          </button>
        </form>
      )}

      {/* ── Danh sách địa chỉ ── */}
      {addresses.length === 0 ? (
        <p style={styles.empty}>Bạn chưa có địa chỉ nào. Thêm địa chỉ để đặt hàng nhanh hơn.</p>
      ) : (
        <div style={styles.addrList}>
          {addresses.map((addr) => (
            <div key={addr.addressId} style={{
              ...styles.addrCard,
              ...(addr.isDefault ? styles.addrCardDefault : {}),
            }}>
              <div style={styles.addrInfo}>
                <p style={styles.addrName}>
                  {addr.receiverName}
                  {addr.isDefault && <span style={styles.defaultBadge}>Mặc định</span>}
                </p>
                <p style={styles.addrText}>{addr.phone}</p>
                <p style={styles.addrText}>
                  {addr.detailAddress}, {addr.ward}, {addr.district}, {addr.province}
                </p>
              </div>

              <div style={styles.addrActions}>
                {!addr.isDefault && (
                  <button
                    onClick={() => handleSetDefault(addr.addressId)}
                    style={styles.actionBtn}
                  >
                    Đặt mặc định
                  </button>
                )}
                <button
                  onClick={() => handleDelete(addr.addressId)}
                  style={{ ...styles.actionBtn, color: '#dc2626' }}
                >
                  Xóa
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

function AddressField({ label, error, children }) {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
      <label style={{ fontSize: '0.8rem', fontWeight: 500, color: '#374151' }}>{label}</label>
      {children}
      {error && <span style={{ fontSize: '0.75rem', color: '#dc2626' }}>{error}</span>}
    </div>
  )
}

// ─── Styles ───────────────────────────────────────────────────────────────────
const styles = {
  page: { display: 'flex', gap: '28px', alignItems: 'flex-start' },
  sidebar: {
    width: '240px', flexShrink: 0,
    background: '#fff', borderRadius: '12px',
    border: '1px solid #e5e7eb', padding: '24px',
  },
  avatar: { display: 'flex', gap: '12px', alignItems: 'center', marginBottom: '24px' },
  avatarCircle: {
    width: '44px', height: '44px', borderRadius: '50%',
    background: '#111827', color: '#fff', display: 'flex',
    alignItems: 'center', justifyContent: 'center',
    fontSize: '1.1rem', fontWeight: 700, flexShrink: 0,
  },
  avatarName:  { fontWeight: 600, fontSize: '0.9rem' },
  avatarEmail: { fontSize: '0.75rem', color: '#9ca3af' },
  nav: { display: 'flex', flexDirection: 'column', gap: '4px' },
  navBtn: {
    padding: '10px 12px', textAlign: 'left',
    background: 'none', border: 'none', borderRadius: '8px',
    cursor: 'pointer', fontSize: '0.875rem', color: '#374151',
  },
  navBtnActive: { background: '#f3f4f6', fontWeight: 600, color: '#111827' },
  content: { flex: 1, minWidth: 0 },
  tabTitle: { fontSize: '1.2rem', fontWeight: 700, marginBottom: '20px' },
  infoCard: {
    background: '#fff', borderRadius: '12px',
    border: '1px solid #e5e7eb', overflow: 'hidden',
  },
  infoRow: {
    display: 'flex', padding: '14px 20px',
    borderBottom: '1px solid #f3f4f6', gap: '12px',
  },
  infoLabel: { width: '160px', flexShrink: 0, color: '#6b7280', fontSize: '0.875rem' },
  infoValue: { color: '#111827', fontSize: '0.875rem', fontWeight: 500 },
  hint: { marginTop: '12px', fontSize: '0.8rem', color: '#9ca3af' },
  addressHeader: {
    display: 'flex', justifyContent: 'space-between',
    alignItems: 'center', marginBottom: '20px',
  },
  addBtn: {
    padding: '8px 16px', background: '#111827', color: '#fff',
    border: 'none', borderRadius: '8px', cursor: 'pointer', fontSize: '0.875rem',
  },
  addressForm: {
    background: '#fff', border: '1px solid #e5e7eb',
    borderRadius: '12px', padding: '24px', marginBottom: '24px',
    display: 'flex', flexDirection: 'column', gap: '14px',
  },
  formGrid: {
    display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '14px',
  },
  input: {
    padding: '9px 12px', borderRadius: '7px',
    border: '1px solid #d1d5db', fontSize: '0.875rem', outline: 'none',
  },
  inputErr: { borderColor: '#f87171' },
  checkboxRow: {
    display: 'flex', alignItems: 'center', gap: '8px',
    fontSize: '0.875rem', cursor: 'pointer',
  },
  saveBtn: {
    alignSelf: 'flex-start', padding: '10px 24px',
    background: '#111827', color: '#fff', border: 'none',
    borderRadius: '8px', cursor: 'pointer', fontWeight: 600,
  },
  empty: { color: '#9ca3af', padding: '40px 0', textAlign: 'center' },
  addrList: { display: 'flex', flexDirection: 'column', gap: '12px' },
  addrCard: {
    background: '#fff', border: '1px solid #e5e7eb',
    borderRadius: '12px', padding: '16px 20px',
    display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: '16px',
  },
  addrCardDefault: { borderColor: '#111827', borderWidth: '1.5px' },
  addrInfo: { flex: 1 },
  addrName: { fontWeight: 600, marginBottom: '4px', display: 'flex', alignItems: 'center', gap: '8px' },
  addrText: { fontSize: '0.875rem', color: '#6b7280', marginTop: '2px' },
  defaultBadge: {
    fontSize: '0.7rem', background: '#111827', color: '#fff',
    padding: '2px 8px', borderRadius: '20px',
  },
  addrActions: { display: 'flex', flexDirection: 'column', gap: '6px', flexShrink: 0 },
  actionBtn: {
    background: 'none', border: '1px solid #e5e7eb', borderRadius: '6px',
    padding: '5px 12px', cursor: 'pointer', fontSize: '0.8rem',
    color: '#374151', whiteSpace: 'nowrap',
  },
}
