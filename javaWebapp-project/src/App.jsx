import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Footer from './components/common/Footer'
import Navbar from './components/common/Navbar'
import { AuthProvider } from './context/AuthContext'
import CartPage from './pages/CartPage'
import CheckoutPage from './pages/CheckoutPage'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import OrderHistoryPage from './pages/OrderHistoryPage'
import ProductDetailPage from './pages/ProductDetailPage'
import ProductListPage from './pages/ProductListPage'
import ProfilePage from './pages/ProfilePage'
import RegisterPage from './pages/RegisterPage'

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Navbar />
        <main>
          <Routes>
            {/* Public */}
            <Route path="/"              element={<HomePage />} />
            <Route path="/products"      element={<ProductListPage />} />
            <Route path="/products/:id"  element={<ProductDetailPage />} />
            <Route path="/login"         element={<LoginPage />} />
            <Route path="/register"      element={<RegisterPage />} />

            {/* Cần đăng nhập */}
            <Route path="/cart"           element={<CartPage />} />
            <Route path="/checkout"       element={<CheckoutPage />} />
            <Route path="/orders"         element={<OrderHistoryPage />} />
            <Route path="/orders/:orderId" element={<OrderHistoryPage />} />
            <Route path="/profile"        element={<ProfilePage />} />
          </Routes>
        </main>
        <Footer />
      </AuthProvider>
    </BrowserRouter>
  )
}
