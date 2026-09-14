// Component nhỏ hiển thị khi đang fetch data
// Props: message (tùy chọn)
export default function LoadingSpinner({ message = 'Đang tải...' }) {
  return (
    <div style={{ textAlign: 'center', padding: '60px 20px', color: '#6b7280' }}>
      <div style={{ fontSize: '2rem', marginBottom: '12px' }}>⟳</div>
      <p>{message}</p>
    </div>
  )
}
