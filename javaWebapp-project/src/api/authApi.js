import api from './axiosInstance'

export const login = (email, password) =>
  api.post('/users/login', { email, password })

export const register = (fullName, email, password, phone) =>
  api.post('/users/register', { fullName, email, password, phone })
