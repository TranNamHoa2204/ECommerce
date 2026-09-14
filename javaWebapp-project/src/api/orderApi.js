import api from './axiosInstance'

// Đặt hàng — body khớp với CreateOrderRequestDTO
export const createOrder = (data) => api.post('/orders', data)

// Lịch sử đơn hàng của user đang đăng nhập
export const getMyOrders = () => api.get('/orders')

// Chi tiết từng item trong đơn
export const getOrderDetails = (orderId) =>
  api.get(`/orders/${orderId}/details`)

// Hủy đơn hàng
export const cancelOrder = (orderId) =>
  api.put(`/orders/${orderId}/cancel`)
