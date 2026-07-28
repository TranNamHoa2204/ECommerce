<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.User" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <title>LapTop Selling Website</title>
</head>
<body>
    <header>
        <div class="container">
            <div class="header-top">
                <img src="../images/Amazon-Logo.png" alt="Amazon Logo" class="logo">
                <div class="dropdown">
                    <button class="dropdown-btn avg-size"> <i class="fa-solid fa-bars"></i> Danh mục</button>
                    <div class="dropdown-content">
                        <a href="">Điện thoại, Tablet</a>
                        <a href="">Laptop</a>
                        <a href="">Tai nghe</a>
                        <a href="">PC, Màn hình</a>
                    </div>
                </div>
                <div class="search-bar">
                    <input type="text" placeholder="Tìm kiếm sản phẩm" class="search-input">
                    <button class="search-btn">Tìm kiếm</button>
                </div>
                <div class="header-right">
                    <a href="#" class="btn-link">
                        <i class="fa-solid fa-cart-shopping"></i>
                        Giỏ hàng
                    </a>
                    <%
                        User currentUser = (User) session.getAttribute("currentUser");
                        if (currentUser != null) {
                    %>
                        <div class="dropdown">
                            <button class="btn-link dropdown-toggle border-0 bg-transparent"
                                    data-bs-toggle="dropdown">
                                <i class="fa-solid fa-user"></i>
                                <%= currentUser.getFullName() %>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="#">Tài khoản của tôi</a></li>
                                <li><a class="dropdown-item" href="#">Đơn hàng</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item text-danger"
                                       href="${pageContext.request.contextPath}/user?action=logout">
                                    Đăng xuất
                                </a></li>
                            </ul>
                        </div>
                    <%
                        } else {
                    %>
                        <a href="${pageContext.request.contextPath}/user?action=login" class="btn-link">
                            <i class="fa-solid fa-user"></i>
                            Đăng nhập
                        </a>
                        <a href="${pageContext.request.contextPath}/user?action=register" class="btn-link">
                            <i class="fa-solid fa-user-plus"></i>
                            Đăng ký
                        </a>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </header>

    <section class="category-menu">
        <a href="#" class="active"><i class="fa-solid fa-laptop"></i> Laptop</a>
        <a href="#"><i class="fa-solid fa-computer"></i> PC</a>
        <a href="#"><i class="fa-solid fa-display"></i> Màn hình</a>
        <!-- <a href="#"><i class="fa-solid fa-microchip"></i> Linh kiện</a>
        <a href="#"><i class="fa-solid fa-print"></i> Máy in</a> -->
    </section>

    <section class="slider container my-4">
        <!-- Thuộc tính data-bs-interval="5000" giúp đổi ảnh sau mỗi 5000ms (5 giây) -->
        <div id="bannerCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="5000">
            
            <!-- Các chấm tròn/vạch chỉ số dưới banner  -->
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="4" aria-label="Slide 5"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="5" aria-label="Slide 6"></button>
            </div>

            <!-- Các hình ảnh Banner (Lưu ý class active ở item đầu tiên) -->
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="../images/banner1.png" class="d-block w-100" alt="Banner 1">
                </div>
                <div class="carousel-item">
                    <img src="../images/banner2.webp" class="d-block w-100" alt="Banner 2">
                </div>
                <div class="carousel-item">
                    <img src="../images/banner3.webp" class="d-block w-100" alt="Banner 3">
                </div>
                <div class="carousel-item">
                    <img src="../images/banner4.webp" class="d-block w-100" alt="Banner 4">
                </div>
                <div class="carousel-item">
                    <img src="../images/banner5.webp" class="d-block w-100" alt="Banner 5">
                </div>
                <div class="carousel-item">
                    <img src="../images/banner6.webp" class="d-block w-100" alt="Banner 6">
                </div>
            </div>

            <!-- Nút bấm quay lại (Prev) -->
            <button class="carousel-control-prev" type="button" data-bs-target="#bannerCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            
            <!-- Nút bấm tiếp theo (Next) -->
            <button class="carousel-control-next" type="button" data-bs-target="#bannerCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </section>

<!-- ================= MỤC MÁY TÍNH LAPTOP & THƯƠNG HIỆU ================= -->
    <section class="laptop-brands container my-4">

        <!-- 1. Tiêu đề mục -->
        <h2 class="fs-4 fw-bold mb-3 text-dark">Máy tính laptop</h2>

        <!-- 2. Danh sách logo thương hiệu -->
        <div class="d-flex flex-wrap gap-2 brand-list">
            <a href="#" class="btn btn-outline-secondary brand-item">MacBook</a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/Asus.webp" alt="ASUS"></a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/Lenovo.webp" alt="Lenovo"></a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/MSI.webp" alt="MSI"></a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/Acer.webp" alt="Acer"></a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/HP.webp" alt="HP"></a>
            <a href="#" class="btn btn-outline-secondary brand-item"><img src="../images/brand/DELL.webp" alt="DELL"></a>
        </div>

    </section>
    <!-- ===================================================================== -->

    <!-- ================= MỤC ƯU ĐÃI LAPTOP NỔI BẬT (SLIDER) ================= -->
    <section class="featured-products container my-5">
        <div class="promo-box p-4 rounded-3">
            
            <!-- Tiêu đề mục -->
            <h2 class="text-center text-white fw-bold fs-4 mb-4">Ưu đãi Laptop nổi bật</h2>

            <!-- Slider Sản phẩm -->
            <div id="productCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="false">
                <div class="carousel-inner">

                    <!-- SLIDE 1 (Chứa 5 sản phẩm đầu tiên) -->
                    <div class="carousel-item active">
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-5 g-3">
                            
                            <!-- Sản phẩm 1 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/ASUS/Laptop Asus Vivobook 14 X1404VA-EB355W.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 4.500.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">ASUS</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop Asus Vivobook 14 X1404VA-EB355W (Core 7 150U/ 16GB/ 512GB/ Win 11 Home)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">21.490.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">25.990.000 đ <span class="text-danger">-17,31%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 2 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/HP/Laptop HP Victus 15 fa2731TX.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 3.500.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">HP</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop HP Victus 15 fa2731TX (B85LNPA) (i5-13420H/ GeForce RTX™ 3050/ 16GB/ 512GB/ Win 11 Home SL)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">25.990.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">29.490.000 đ <span class="text-danger">-11,87%</span></div>
                                    <div class="rating mt-2 small text-warning"><i class="fa-solid fa-star"></i> 4.0</div>
                                </div>
                            </div>

                            <!-- Sản phẩm 3 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/ACER/Laptop Acer Aspire 7 A715-59G-55MD.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 1.000.000 đ</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">ACER</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop Acer Aspire 7 A715-59G-55MD (i5-13420H/ GeForce RTX™ 3050/ 16GB/ 512GB/ Win 11 Home SL)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">24.490.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">27.990.000 đ <span class="text-danger">-4,41%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 4 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/HP/Laptop HP 250R G10 - C3SH7AT.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 5.600.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">HP</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop HP 250R G10 - C3SH7AT (Core 5 120U/ 16GB/ 512GB/ Windows 11 Home SL)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">20.990.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">26.590.000 đ <span class="text-danger">-21,06%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 5 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/HP/Laptop HP OmniBook 5 AI 16-af1054TU - C1MN8PA.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 5.400.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">HP</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop HP OmniBook 5 AI 16-af1054TU - C1MN8PA (Ultra 7-255U/ 32GB/ 512GB/ Win 11 Home SL + Office)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">31.590.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">36.990.000 đ <span class="text-danger">-14,6%</span></div>
                                    <div class="rating mt-2 small text-warning"><i class="fa-solid fa-star"></i> 4.0</div>
                                </div>
                            </div>

                        </div>
                    </div>

                    <!-- SLIDE 2 (Chứa các sản phẩm tiếp theo khi nhấn nút Next) -->
                    <div class="carousel-item">
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-5 g-3">
                            <!-- Sản phẩm 6 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/DELL/Laptop Dell 14 DC14250 DC4C5386W.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 4.500.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">DELL</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop Dell 14 DC14250 DC4C5386W (Core 5 120U/ 16GB/ 512GB/ Win 11 Home SL + Office)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">23.390.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">24.190.000 đ <span class="text-danger">-17,31%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 7 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/HP/Laptop HP 240R G9 - AX3C6AT.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 3.500.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">HP</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop HP 240R G9 - AX3C6AT (i3-1315U/ 8GB/ 512GB/ Windows 11 Home SL)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">14.490.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">16.390.000 đ <span class="text-danger">-11,87%</span></div>
                                    <div class="rating mt-2 small text-warning"><i class="fa-solid fa-star"></i> 4.0</div>
                                </div>
                            </div>

                            <!-- Sản phẩm 8 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/ACER/Laptop Acer Aspire Go 14 AG14-72P-563L.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 1.000.000 đ</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">ACER</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop Acer Aspire Go 14 AG14-72P-563L (Core 5 120U/ 16GB/ 512GB/ Windows 11 Home SL)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">19.490.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">20.490.000 đ <span class="text-danger">-4,41%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 9 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/ASUS/Laptop Asus TUF Gaming A15 FA506NCG-HN184W.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 5.600.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">ASUS</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop ASUS TUF Gaming A15 FA506NCG-HN184W</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">22.990.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">23.290.000 đ <span class="text-danger">-21,06%</span></div>
                                </div>
                            </div>

                            <!-- Sản phẩm 10 -->
                            <div class="col">
                                <div class="product-card h-100 p-3 bg-white rounded shadow-sm position-relative">
                                    <div class="text-center mb-3">
                                        <img src="../images/laptop/MSI/Laptop Msi Katana B14WEK-286VN.webp" alt="Laptop" class="img-fluid product-img">
                                    </div>
                                    <div class="badge-tags d-flex justify-content-between align-items-center mb-2">
                                        <span class="badge-save">TIẾT KIỆM 5.400.000 đ</span>
                                        <span class="badge-installment">TRẢ GÓP 0%</span>
                                    </div>
                                    <p class="brand-text text-uppercase text-muted small mb-1">MSI</p>
                                    <h3 class="product-title fs-6 mb-2 text-dark">Laptop Msi Katana B14WEK-286VN (i5-14450HX/ GeForce RTX™ 5050/ 16GB/ 512GB/ Win 11 Home)</h3>
                                    <span class="combo-tag d-inline-block mb-2">COMBO GIẢM ~ 50.000 đ</span>
                                    <div class="product-price fw-bold text-primary mb-1">38.990.000 đ</div>
                                    <div class="old-price small text-muted text-decoration-line-through">41.990.000 đ <span class="text-danger">-14,6%</span></div>
                                    <div class="rating mt-2 small text-warning"><i class="fa-solid fa-star"></i> 4.0</div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- Nút điều hướng qua lại thiết kế tinh gọn nằm 2 bên đè lên khung -->
                <button class="carousel-control-prev custom-nav-btn" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                    <i class="fa-solid fa-chevron-left fs-4"></i>
                </button>
                <button class="carousel-control-next custom-nav-btn" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                    <i class="fa-solid fa-chevron-right fs-4"></i>
                </button>
            </div>

        </div>
    </section>
    <!-- ===================================================================== -->

    <!-- ================= DANH MỤC SẢN PHẨM CHÍNH ================= -->
    <section class="main-products container my-5">
        
        <!-- 1. Thanh Bộ lọc / Sắp xếp theo -->
        <div class="d-flex flex-wrap align-items-center justify-content-between mb-4 border-bottom pb-3">
            <h2 class="fs-4 fw-bold text-dark mb-2 mb-sm-0">Sắp xếp theo</h2>
            <div class="d-flex flex-wrap gap-2 sorting-buttons">
                <button class="btn btn-outline-primary btn-sm rounded-pill active"><i class="fa-solid fa-star me-1"></i>Phổ biến</button>
                <button class="btn btn-light btn-sm rounded-pill text-secondary"><i class="fa-solid fa-fire me-1"></i>Khuyến mãi HOT</button>
                <button class="btn btn-light btn-sm rounded-pill text-secondary"><i class="fa-solid fa-arrow-trend-up me-1"></i>Giá Thấp - Cao</button>
                <button class="btn btn-light btn-sm rounded-pill text-secondary"><i class="fa-solid fa-arrow-trend-down me-1"></i>Giá Cao - Thấp</button>
            </div>
        </div>

        <!-- 2. Lưới Sản phẩm (Grid 5 cột) -->
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-5 g-3">
            
            <!-- Sản phẩm 1 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/MSI/Laptop Msi Cyborg 15 C13WEO-418VN.webp" alt="Laptop MSI" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Msi Cyborg 15 C13WEO-418VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 2 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Bạn có thể copy nhân bản thêm các thẻ col sản phẩm 3, 4, 5... cho đủ hàng bên dưới -->

            <!-- Sản phẩm 3 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/LENOVO/Laptop Lenovo LOQ 15AHP11 - 83TN0040VN.webp" alt="Laptop Lenovo" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 7 250/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home SL
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Lenovo LOQ 15AHP11 - 83TN0040VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">42.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">46.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 4 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/LENOVO/Laptop Lenovo Legion 5 15AHP11 - 83Q7001HVN.webp" alt="Laptop Lenovo" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 7 250/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home SL + Office
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Lenovo Legion 5 15AHP11 - 83Q7001HVN
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">53.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">55.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 5 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/LENOVO/Laptop Lenovo ThinkBook 14 G9 IRL - 21UY008TVN.webp" alt="Laptop Lenovo" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ Intel Graphics/ 16GB/ 512GB/ Windows 11 Home SL
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Lenovo ThinkBook 14 G9 IRL - 21UY008TVN
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 6 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/HP/Laptop HP 14 em0023AU - D0BG7PA.webp" alt="Laptop HP" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7520U/ 16GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop HP 14 em0023AU - D0BG7PA
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">23.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Bạn có thể copy nhân bản thêm các thẻ col sản phẩm 3, 4, 5... cho đủ hàng bên dưới -->

            <!-- Sản phẩm 7 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/MSI/Laptop Msi Cyborg 15 C13WEO-418VN.webp" alt="Laptop MSI" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Msi Cyborg 15 C13WEO-418VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 8 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 9 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/MSI/Laptop Msi Cyborg 15 C13WEO-418VN.webp" alt="Laptop MSI" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Msi Cyborg 15 C13WEO-418VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 10 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Bạn có thể copy nhân bản thêm các thẻ col sản phẩm 3, 4, 5... cho đủ hàng bên dưới -->

            <!-- Sản phẩm 11 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/MSI/Laptop Msi Cyborg 15 C13WEO-418VN.webp" alt="Laptop MSI" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Msi Cyborg 15 C13WEO-418VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 12 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 13 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Bạn có thể copy nhân bản thêm các thẻ col sản phẩm 3, 4, 5... cho đủ hàng bên dưới -->

            <!-- Sản phẩm 14 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <!-- Nhãn giảm giá & trả góp tuyệt đối góc trên -->
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2 pointer-events-none">
                        <span class="badge bg-danger text-white fs-7">Giảm 14%</span>
                        <span class="badge bg-light text-primary border border-primary fs-7">Trả góp 0%</span>
                    </div>
                    
                    <!-- Hình ảnh sản phẩm -->
                    <div class="text-center my-4">
                        <img src="../images/laptop/MSI/Laptop Msi Cyborg 15 C13WEO-418VN.webp" alt="Laptop MSI" class="img-fluid main-prod-img">
                    </div>

                    <!-- Thông số cấu hình tóm tắt -->
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Core i5-13420H/ GeForce RTX™ 5050/ 16GB/ 512GB/ Windows 11 Home
                    </div>

                    <!-- Tên sản phẩm -->
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Msi Cyborg 15 C13WEO-418VN 
                    </h3>

                    <!-- Giá tiền -->
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">34.990.000đ</span>
                        <span class="text-muted text-decoration-line-through small">35.990.000đ</span>
                    </div>

                    <!-- Quà tặng kèm -->
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        Tặng voucher mua RAM LAPTOP và Chuột/Bàn phím/Webcam...
                    </div>

                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

            <!-- Sản phẩm 15 -->
            <div class="col">
                <div class="main-product-card h-100 p-3 bg-white rounded border position-relative d-flex flex-column justify-content-between">
                    <div class="card-badges position-absolute top-0 start-0 end-0 d-flex justify-content-between p-2">
                        <span class="badge bg-danger text-white">Giảm 4%</span>
                        <span class="badge bg-light text-primary border border-primary">Trả góp 0%</span>
                    </div>
                    <div class="text-center my-4">
                        <img src="../images/laptop/Acer/Laptop Acer Aspire Lite 15 AL15-49P-R6XX.webp" alt="Laptop Acer" class="img-fluid main-prod-img">
                    </div>
                    <div class="specs-text text-center text-danger small fw-bold mb-2">
                        Ryzen 5 7430U/ 8GB/ 512GB/ Windows 11 Home SL
                    </div>
                    <h3 class="main-product-title fs-6 text-dark fw-bold mb-2">
                        Laptop Acer Aspire Lite 15 AL15-49P-R6XX
                    </h3>
                    <div class="mb-2">
                        <span class="text-danger fw-bold fs-5 me-1">19.390.000đ</span>
                        <span class="text-muted text-decoration-line-through small">19.990.000đ</span>
                    </div>
                    <div class="gift-box p-2 bg-light rounded border text-muted small mb-3">
                        trả góp 0% lãi suất, tối đa 12 tháng, trả trước từ 10% qua CTT...
                    </div>
                    <!-- Nút thêm vào giỏ -->
                    <button class="btn btn-primary btn-sm w-100 add-to-cart-btn">
                        <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                    </button>
                </div>
            </div>

        </div>
    </section>

    <footer class="web-footer"></footer>
    
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/index.js"></script>
</body>
</html>