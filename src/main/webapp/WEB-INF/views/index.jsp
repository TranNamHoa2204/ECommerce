<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="java.util.*" %>
<%--
    index.jsp - Trang chủ website bán quần áo FashionHub
    Ghi chú: Dữ liệu bên dưới (categories, products...) là dữ liệu MẪU được
    tạo bằng scriptlet để trang chạy được ngay không cần Database/Servlet.
    Trong thực tế, hãy để Servlet/Controller truy vấn DB rồi forward attribute
    sang JSP này (request.setAttribute("featuredProducts", list)) và xoá
    phần scriptlet demo bên dưới.
--%>
<c:set var="pageTitle" value="Trang chủ" scope="request"/>

<%
    // ================== DỮ LIỆU MẪU (DEMO) ==================
    String ctx = request.getContextPath();

    // ---- Danh mục nổi bật ----
    List<Map<String,String>> categories = new ArrayList<>();
    String[][] catData = {
        {"Áo Nam", "https://picsum.photos/seed/cat-ao-nam/300/300", "ao-nam"},
        {"Quần Nam", "https://picsum.photos/seed/cat-quan-nam/300/300", "quan-nam"},
        {"Áo Nữ", "https://picsum.photos/seed/cat-ao-nu/300/300", "ao-nu"},
        {"Váy - Đầm", "https://picsum.photos/seed/cat-vay-dam/300/300", "vay-dam"},
        {"Giày - Dép", "https://picsum.photos/seed/cat-giay-dep/300/300", "giay-dep"},
        {"Phụ kiện", "https://picsum.photos/seed/cat-phu-kien/300/300", "phu-kien"}
    };
    for (String[] c : catData) {
        Map<String,String> m = new HashMap<>();
        m.put("name", c[0]); m.put("image", c[1]); m.put("slug", c[2]);
        categories.add(m);
    }
    request.setAttribute("categories", categories);

    // ---- Hàm tạo danh sách sản phẩm mẫu ----
    // fields: id, name, price, oldPrice, image, colors, badge
    List<Map<String,Object>> featured = new ArrayList<>();
    Object[][] featuredData = {
        {1, "Áo Polo Cotton Basic", 450000, 0, "featured1", "Đen | Trắng | Xanh", ""},
        {2, "Áo Sơ Mi Linen Trơn", 590000, 690000, "featured2", "Trắng | Be", "-15%"},
        {3, "Quần Jean Slim Fit", 750000, 0, "featured3", "Xanh đậm | Đen", ""},
        {4, "Áo Thun Local Brand", 320000, 0, "featured4", "Trắng | Đen | Xám", "Mới"},
        {5, "Chân Váy Xếp Ly", 480000, 550000, "featured5", "Đen | Kem", "-13%"},
        {6, "Áo Khoác Bomber", 890000, 0, "featured6", "Đen | Xanh rêu", ""},
        {7, "Đầm Suông Dạo Phố", 620000, 0, "featured7", "Hồng | Trắng", "Mới"},
        {8, "Giày Sneaker Trắng", 990000, 1200000, "featured8", "Trắng | Be", "-18%"}
    };
    for (Object[] d : featuredData) {
        Map<String,Object> m = new HashMap<>();
        m.put("id", d[0]); m.put("name", d[1]); m.put("price", d[2]);
        m.put("oldPrice", d[3]); m.put("image", "https://picsum.photos/seed/"+d[4]+"/400/500");
        m.put("colors", d[5]); m.put("badge", d[6]);
        featured.add(m);
    }
    request.setAttribute("featuredProducts", featured);

    // ---- Sản phẩm mới ----
    List<Map<String,Object>> newProducts = new ArrayList<>();
    Object[][] newData = {
        {9, "Áo Hoodie Nỉ Bông", 550000, "new1", "Đen | Xám"},
        {10, "Quần Short Kaki", 350000, "new2", "Be | Xanh"},
        {11, "Áo Blazer Nữ Công Sở", 780000, "new3", "Đen | Nude"}
    };
    for (Object[] d : newData) {
        Map<String,Object> m = new HashMap<>();
        m.put("id", d[0]); m.put("name", d[1]); m.put("price", d[2]);
        m.put("image", "https://picsum.photos/seed/"+d[3]+"/400/500"); m.put("colors", d[4]);
        newProducts.add(m);
    }
    request.setAttribute("newProducts", newProducts);

    // ---- Best Seller ----
    List<Map<String,Object>> bestSellers = new ArrayList<>();
    Object[][] bestData = {
        {12, "Áo Thun Trơn Unisex", 199000, "best1", "Nhiều màu"},
        {13, "Quần Tây Ống Đứng", 650000, "best2", "Đen | Xanh navy"},
        {14, "Đầm Body Dự Tiệc", 720000, "best3", "Đỏ | Đen"},
        {15, "Dép Sandal Nam", 280000, "best4", "Đen | Nâu"}
    };
    for (Object[] d : bestData) {
        Map<String,Object> m = new HashMap<>();
        m.put("id", d[0]); m.put("name", d[1]); m.put("price", d[2]);
        m.put("image", "https://picsum.photos/seed/"+d[3]+"/400/500"); m.put("colors", d[4]);
        bestSellers.add(m);
    }
    request.setAttribute("bestSellers", bestSellers);

    // ---- Thương hiệu ----
    String[] brands = {"NIKA", "URBANO", "COTONA", "ZENFA", "LEVICO", "STYLEX"};
    request.setAttribute("brands", brands);
%>

<jsp:include page="header.jsp" />

<main>

    <!-- ============ BANNER LỚN (CAROUSEL) ============ -->
    <section class="hero-banner">
        <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" class="active"></button>
                <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1"></button>
                <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="2"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://picsum.photos/seed/banner1/1600/550" class="d-block w-100" alt="Bộ sưu tập Thu Đông">
                    <div class="carousel-caption">
                        <h1 class="fw-bold">BỘ SƯU TẬP THU ĐÔNG 2026</h1>
                        <p class="fs-5 d-none d-md-block">Phong cách mới - Ưu đãi đến 30%</p>
                        <a href="${pageContext.request.contextPath}/khuyen-mai" class="btn btn-light btn-lg mt-2 fw-600">Khám phá ngay</a>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="https://picsum.photos/seed/banner2/1600/550" class="d-block w-100" alt="Sale cuối tuần">
                    <div class="carousel-caption">
                        <h1 class="fw-bold">SALE CUỐI TUẦN</h1>
                        <p class="fs-5 d-none d-md-block">Giảm giá đến 50% toàn bộ sản phẩm nữ</p>
                        <a href="${pageContext.request.contextPath}/khuyen-mai" class="btn btn-light btn-lg mt-2 fw-600">Mua ngay</a>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="https://picsum.photos/seed/banner3/1600/550" class="d-block w-100" alt="Hàng mới về">
                    <div class="carousel-caption">
                        <h1 class="fw-bold">HÀNG MỚI VỀ MỖI TUẦN</h1>
                        <p class="fs-5 d-none d-md-block">Cập nhật xu hướng thời trang mới nhất</p>
                        <a href="${pageContext.request.contextPath}/san-pham-moi" class="btn btn-light btn-lg mt-2 fw-600">Xem ngay</a>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon"></span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon"></span>
            </button>
        </div>
    </section>

    <!-- ============ DANH MỤC NỔI BẬT ============ -->
    <section class="category-section py-5">
        <div class="container">
            <h2 class="section-title text-center mb-4">Danh Mục Nổi Bật</h2>
            <div class="row g-3 justify-content-center">
                <c:forEach var="cat" items="${categories}">
                    <div class="col-6 col-md-4 col-lg-2">
                        <a href="${pageContext.request.contextPath}/danh-muc/${cat.slug}" class="category-card text-decoration-none">
                            <div class="category-img-wrap">
                                <img src="${cat.image}" alt="${cat.name}">
                            </div>
                            <p class="text-center fw-600 mt-2 mb-0 text-dark">${cat.name}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- ============ SẢN PHẨM NỔI BẬT ============ -->
    <section class="featured-section py-5 bg-light-section">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="section-title mb-0">Sản Phẩm Nổi Bật</h2>
                <a href="${pageContext.request.contextPath}/san-pham" class="btn btn-outline-primary">Xem tất cả <i class="bi bi-arrow-right"></i></a>
            </div>
            <div class="row g-4">
                <c:forEach var="p" items="${featuredProducts}">
                    <div class="col-6 col-md-4 col-lg-3">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/san-pham/${p.id}" class="text-decoration-none text-dark">
                                <div class="product-img-wrap">
                                    <img src="${p.image}" alt="${p.name}">
                                    <c:if test="${not empty p.badge}">
                                        <span class="badge-product ${p.badge == 'Mới' ? 'bg-success' : 'bg-danger'}">${p.badge}</span>
                                    </c:if>
                                    <div class="product-actions">
                                        <button type="button" class="btn-icon-round" title="Thêm vào yêu thích"><i class="bi bi-heart"></i></button>
                                        <button type="button" class="btn-icon-round" title="Xem nhanh"><i class="bi bi-eye"></i></button>
                                    </div>
                                </div>
                                <div class="product-info">
                                    <p class="product-name">${p.name}</p>
                                    <p class="product-price">
                                        <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>đ
                                        <c:if test="${p.oldPrice > 0}">
                                            <span class="old-price"><fmt:formatNumber value="${p.oldPrice}" type="number" groupingUsed="true"/>đ</span>
                                        </c:if>
                                    </p>
                                    <p class="product-colors">${p.colors}</p>
                                </div>
                            </a>
                            <button type="button" class="btn btn-primary btn-add-cart w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- ============ SẢN PHẨM MỚI ============ -->
    <section class="new-section py-5">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="section-title mb-0">Sản Phẩm Mới</h2>
                <a href="${pageContext.request.contextPath}/san-pham-moi" class="btn btn-outline-primary">Xem tất cả <i class="bi bi-arrow-right"></i></a>
            </div>
            <div class="row g-4">
                <c:forEach var="p" items="${newProducts}">
                    <div class="col-6 col-md-4">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/san-pham/${p.id}" class="text-decoration-none text-dark">
                                <div class="product-img-wrap">
                                    <img src="${p.image}" alt="${p.name}">
                                    <span class="badge-product bg-success">Mới</span>
                                </div>
                                <div class="product-info">
                                    <p class="product-name">${p.name}</p>
                                    <p class="product-price"><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>đ</p>
                                    <p class="product-colors">${p.colors}</p>
                                </div>
                            </a>
                            <button type="button" class="btn btn-primary btn-add-cart w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- ============ BEST SELLER ============ -->
    <section class="bestseller-section py-5 bg-light-section">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="section-title mb-0"><i class="bi bi-fire text-danger"></i> Best Seller</h2>
                <a href="${pageContext.request.contextPath}/best-seller" class="btn btn-outline-primary">Xem tất cả <i class="bi bi-arrow-right"></i></a>
            </div>
            <div class="row g-4">
                <c:forEach var="p" items="${bestSellers}">
                    <div class="col-6 col-md-3">
                        <div class="product-card">
                            <a href="${pageContext.request.contextPath}/san-pham/${p.id}" class="text-decoration-none text-dark">
                                <div class="product-img-wrap">
                                    <img src="${p.image}" alt="${p.name}">
                                    <span class="badge-product bg-warning text-dark">Hot</span>
                                </div>
                                <div class="product-info">
                                    <p class="product-name">${p.name}</p>
                                    <p class="product-price"><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>đ</p>
                                    <p class="product-colors">${p.colors}</p>
                                </div>
                            </a>
                            <button type="button" class="btn btn-primary btn-add-cart w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- ============ BANNER GIỮA TRANG ============ -->
    <section class="mid-banner py-5">
        <div class="container">
            <a href="${pageContext.request.contextPath}/khuyen-mai" class="d-block position-relative mid-banner-link">
                <img src="https://picsum.photos/seed/midbanner/1600/400" class="w-100 rounded-4" alt="Ưu đãi đặc biệt">
                <div class="mid-banner-caption">
                    <h3 class="fw-bold text-white">ƯU ĐÃI ĐẶC BIỆT CUỐI TUẦN</h3>
                    <p class="text-white-50 mb-3">Giảm ngay 100.000đ cho đơn hàng từ 800.000đ</p>
                    <span class="btn btn-light fw-600">Mua ngay <i class="bi bi-arrow-right"></i></span>
                </div>
            </a>
        </div>
    </section>

    <!-- ============ THEO THƯƠNG HIỆU ============ -->
    <section class="brand-section py-5 bg-light-section">
        <div class="container">
            <h2 class="section-title text-center mb-4">Theo Thương Hiệu</h2>
            <div class="row g-3 justify-content-center">
                <c:forEach var="b" items="${brands}">
                    <div class="col-4 col-md-2">
                        <a href="${pageContext.request.contextPath}/thuong-hieu/${b}" class="brand-card d-flex align-items-center justify-content-center text-decoration-none">
                            <span class="fw-bold text-uppercase">${b}</span>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- ============ THEO GIỚI TÍNH ============ -->
    <section class="gender-section py-5">
        <div class="container">
            <h2 class="section-title text-center mb-4">Theo Giới Tính</h2>
            <div class="row g-4">
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/danh-muc/nam" class="gender-card text-decoration-none d-block">
                        <img src="https://picsum.photos/seed/gender-male/800/450" alt="Thời trang Nam">
                        <div class="gender-overlay">
                            <h3 class="text-white fw-bold mb-2">THỜI TRANG NAM</h3>
                            <span class="btn btn-light btn-sm fw-600">Mua sắm ngay</span>
                        </div>
                    </a>
                </div>
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/danh-muc/nu" class="gender-card text-decoration-none d-block">
                        <img src="https://picsum.photos/seed/gender-female/800/450" alt="Thời trang Nữ">
                        <div class="gender-overlay">
                            <h3 class="text-white fw-bold mb-2">THỜI TRANG NỮ</h3>
                            <span class="btn btn-light btn-sm fw-600">Mua sắm ngay</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </section>

</main>

<jsp:include page="footer.jsp" />
