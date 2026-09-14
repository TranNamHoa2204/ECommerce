import api from './axiosInstance'

// Lấy danh sách địa chỉ — truyền userId vì controller chưa dùng JWT
export const getAddresses     = (userId) => api.get(`/addresses/user/${userId}`)
export const getDefaultAddress = (userId) => api.get(`/addresses/user/${userId}/default`)

export const addAddress = (userId, data) =>
  api.post(`/addresses/user/${userId}`, data)

export const updateAddress = (addressId, userId, data) =>
  api.put(`/addresses/${addressId}/user/${userId}`, data)

export const deleteAddress = (addressId, userId) =>
  api.delete(`/addresses/${addressId}/user/${userId}`)

export const setDefaultAddress = (addressId, userId) =>
  api.put(`/addresses/${addressId}/user/${userId}/default`)
