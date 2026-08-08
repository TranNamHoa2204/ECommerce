<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  header.jsp
  Header dùng chung cho toàn bộ website, include vào các trang bằng:
  <jsp:include page="header.jsp" />
--%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:if test="${not empty pageTitle}">${pageTitle} - </c:if>FashionHub</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ============ TOP BAR ============ -->
<div class="top-bar d-none d-md-block">
    <div class="container d-flex justify-content-between align-items-center py-1">
        <div class="small">
            <i class="bi bi-truck"></i> Miễn phí vận chuyển cho đơn hàng từ 500.000đ
        </div>
        <div class="small d-flex gap-3">
            <a href="${pageContext.request.contextPath}/lien-he" class="text-white text-decoration-none"><i class="bi bi-telephone"></i> Hotline: 1900 1234</a>
            <a href="${pageContext.request.contextPath}/theo-doi-don-hang" class="text-white text-decoration-none"><i class="bi bi-box-seam"></i> Theo dõi đơn hàng</a>
        </div>
    </div>
</div>

<!-- ============ HEADER CHÍNH ============ -->
<header class="main-header sticky-top">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light py-2">

            <!-- Logo -->
            <a class="navbar-brand fw-bold fs-3 me-4" href="${pageContext.request.contextPath}/index.jsp">
                <i class="bi bi-bag-heart-fill text-primary"></i> FashionHub
            </a>

            <!-- Toggle mobile -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="mainNavbar">

                <!-- Menu danh mục -->
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle fw-500" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-grid-fill"></i> Danh mục
                        </a>
                        <ul class="dropdown-menu shadow">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/ao-nam">Áo Nam</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/quan-nam">Quần Nam</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/ao-nu">Áo Nữ</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/vay-dam">Váy - Đầm</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/giay-dep">Giày - Dép</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/danh-muc/phu-kien">Phụ kiện</a></li>
                        </ul>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/san-pham-moi">Sản phẩm mới</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/best-seller">Best Seller</a></li>
                    <li class="nav-item"><a class="nav-link text-danger fw-600" href="${pageContext.request.contextPath}/khuyen-mai">Khuyến mãi</a></li>
                </ul>

                <!-- Ô tìm kiếm -->
                <form class="d-flex search-form me-3 mb-2 mb-lg-0" action="${pageContext.request.contextPath}/tim-kiem" method="get">
                    <div class="input-group">
                        <input type="text" name="keyword" class="form-control" placeholder="Bạn muốn tìm gì hôm nay?"
                               value="${param.keyword}">
                        <button class="btn btn-primary" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </form>

                <!-- Icon tài khoản & giỏ hàng -->
                <ul class="navbar-nav align-items-lg-center gap-lg-2">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle fs-5"></i>
                            <span class="d-lg-none">Tài khoản</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end shadow">
                            <c:choose>
                                <c:when test="${not empty sessionScope.currentUser}">
                                    <li><span class="dropdown-item-text">Xin chào, ${sessionScope.currentUser.fullName}</span></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tai-khoan">Thông tin tài khoản</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/don-hang-cua-toi">Đơn hàng của tôi</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link position-relative" href="${pageContext.request.contextPath}/gio-hang">
                            <i class="bi bi-cart3 fs-5"></i>
                            <span class="d-lg-none ms-1">Giỏ hàng</span>
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger cart-badge">
                                ${empty sessionScope.cartCount ? 0 : sessionScope.cartCount}
                            </span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</header>
