<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  footer.jsp
  Footer dùng chung cho toàn bộ website, include vào các trang bằng:
  <jsp:include page="footer.jsp" />
--%>

<!-- ============ NEWSLETTER ============ -->
<section class="newsletter-section py-4">
    <div class="container">
        <div class="row align-items-center g-3">
            <div class="col-lg-6">
                <h5 class="fw-bold mb-1 text-white"><i class="bi bi-envelope-heart"></i> Đăng ký nhận ưu đãi</h5>
                <p class="mb-0 text-white-50 small">Nhận ngay voucher 10% cho đơn hàng đầu tiên khi đăng ký email.</p>
            </div>
            <div class="col-lg-6">
                <form class="d-flex gap-2" action="${pageContext.request.contextPath}/dang-ky-nhan-tin" method="post">
                    <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn" required>
                    <button type="submit" class="btn btn-light fw-600 text-nowrap">Đăng ký</button>
                </form>
            </div>
        </div>
    </div>
</section>

<!-- ============ FOOTER ============ -->
<footer class="main-footer pt-5 pb-3">
    <div class="container">
        <div class="row g-4">
            <!-- Giới thiệu -->
            <div class="col-lg-3 col-md-6">
                <h5 class="fw-bold mb-3"><i class="bi bi-bag-heart-fill text-primary"></i> FashionHub</h5>
                <p class="small text-secondary">FashionHub - Thời trang cho mọi phong cách. Chúng tôi mang đến những sản phẩm chất lượng với giá cả hợp lý nhất.</p>
                <div class="d-flex gap-2 mt-3">
                    <a href="#" class="social-icon"><i class="bi bi-facebook"></i></a>
                    <a href="#" class="social-icon"><i class="bi bi-instagram"></i></a>
                    <a href="#" class="social-icon"><i class="bi bi-tiktok"></i></a>
                    <a href="#" class="social-icon"><i class="bi bi-youtube"></i></a>
                </div>
            </div>

            <!-- Về chúng tôi -->
            <div class="col-lg-3 col-md-6">
                <h6 class="fw-bold mb-3">VỀ CHÚNG TÔI</h6>
                <ul class="list-unstyled footer-links small">
                    <li><a href="${pageContext.request.contextPath}/gioi-thieu">Giới thiệu</a></li>
                    <li><a href="${pageContext.request.contextPath}/he-thong-cua-hang">Hệ thống cửa hàng</a></li>
                    <li><a href="${pageContext.request.contextPath}/tuyen-dung">Tuyển dụng</a></li>
                    <li><a href="${pageContext.request.contextPath}/tin-tuc">Tin tức</a></li>
                </ul>
            </div>

            <!-- Chính sách -->
            <div class="col-lg-3 col-md-6">
                <h6 class="fw-bold mb-3">CHÍNH SÁCH</h6>
                <ul class="list-unstyled footer-links small">
                    <li><a href="${pageContext.request.contextPath}/chinh-sach-doi-tra">Chính sách đổi trả</a></li>
                    <li><a href="${pageContext.request.contextPath}/chinh-sach-van-chuyen">Chính sách vận chuyển</a></li>
                    <li><a href="${pageContext.request.contextPath}/chinh-sach-bao-mat">Chính sách bảo mật</a></li>
                    <li><a href="${pageContext.request.contextPath}/huong-dan-mua-hang">Hướng dẫn mua hàng</a></li>
                </ul>
            </div>

            <!-- Liên hệ -->
            <div class="col-lg-3 col-md-6">
                <h6 class="fw-bold mb-3">LIÊN HỆ</h6>
                <ul class="list-unstyled footer-links small">
                    <li><i class="bi bi-geo-alt"></i> 123 Nguyễn Văn Cừ, Q.5, TP.HCM</li>
                    <li><i class="bi bi-telephone"></i> 1900 1234</li>
                    <li><i class="bi bi-envelope"></i> support@fashionhub.vn</li>
                    <li><i class="bi bi-clock"></i> 8:00 - 22:00 (T2 - CN)</li>
                </ul>
            </div>
        </div>

        <hr class="my-4 border-secondary">

        <div class="row align-items-center">
            <div class="col-md-6 small text-secondary">
                &copy; 2026 FashionHub. All rights reserved.
            </div>
            <div class="col-md-6 text-md-end mt-2 mt-md-0">
                <span class="small text-secondary me-2">Thanh toán:</span>
                <i class="bi bi-credit-card fs-5 mx-1"></i>
                <i class="bi bi-wallet2 fs-5 mx-1"></i>
                <i class="bi bi-cash-coin fs-5 mx-1"></i>
                <i class="bi bi-bank fs-5 mx-1"></i>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
