import api from './axiosInstance'

// Lấy tất cả sản phẩm đang active
export const getAllProducts = () => api.get('/products')

// Lấy sản phẩm theo ID (kèm variants và images)
export const getProductById = (id) => api.get(`/products/${id}`)

// Tìm kiếm theo tên
export const searchProducts = (keyword) =>
  api.get('/products/search', { params: { keyword } })

// Lọc theo danh mục
export const getProductsByCategory = (categoryId) =>
  api.get('/products/category', { params: { categoryId } })

// Lọc theo thương hiệu
export const getProductsByBrand = (brandId) =>
  api.get('/products/brand', { params: { brandId } })

// Lấy variants của sản phẩm
export const getVariantsByProductId = (productId) =>
  api.get(`/products/${productId}/variants`)

// Lấy ảnh của sản phẩm (theo màu)
export const getImagesByProductId = (productId) =>
  api.get(`/products/${productId}/images`)
