// Component hiển thị lỗi — dùng ở mọi trang khi fetch thất bại
export default function ErrorMessage({ message = 'Đã có lỗi xảy ra' }) {
  return (
    <div style={{
      margin: '20px auto', maxWidth: '500px', padding: '16px',
      backgroundColor: '#fef2f2', border: '1px solid #fecaca',
      borderRadius: '8px', color: '#dc2626', textAlign: 'center',
    }}>
      ⚠ {message}
    </div>
  )
}
