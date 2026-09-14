import api from './axiosInstance'

// Lấy giỏ hàng của user đang đăng nhập
export const getMyCart = () => api.get('/cart')

// Thêm hoặc cập nhật sản phẩm trong giỏ
export const addToCart = (variantId, quantity) =>
  api.post('/cart/items', { variantId, quantity })

// Cập nhật số lượng một item
export const updateCartItem = (cartItemId, quantity) =>
  api.put(`/cart/items/${cartItemId}`, null, { params: { quantity } })

// Xóa một item
export const deleteCartItem = (cartItemId) =>
  api.delete(`/cart/items/${cartItemId}`)

// Xóa toàn bộ giỏ
export const clearCart = () => api.delete('/cart/clear')
