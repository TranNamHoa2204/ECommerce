import api from './axiosInstance'

export const getAllCategories = () => api.get('/categories')
export const getCategoryById = (id) => api.get(`/categories/${id}`)
