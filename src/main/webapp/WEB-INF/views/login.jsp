<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        /* Header giữ nguyên style trang chủ */
        header {
            background-color: #dc3545;
            padding: 12px 0;
        }
        .header-inner {
            max-width: 1700px;
            width: 95%;
            margin: auto;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .logo { width: 150px; height: auto; }
        .header-inner a {
            color: white;
            text-decoration: none;
            font-size: 18px;
        }
        .header-inner a:hover { color: #ffd0d0; }

        /* Card đăng nhập */
        .login-wrapper {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 16px;
        }
        .login-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px 36px;
            width: 100%;
            max-width: 440px;
        }
        .login-card h2 {
            font-size: 24px;
            font-weight: 700;
            color: #222;
            margin-bottom: 6px;
        }
        .login-card .subtitle {
            font-size: 14px;
            color: #888;
            margin-bottom: 28px;
        }
        .form-label {
            font-weight: 600;
            font-size: 14px;
            color: #333;
        }
        .form-control {
            border-radius: 8px;
            height: 44px;
            font-size: 15px;
        }
        .form-control:focus {
            border-color: #dc3545;
            box-shadow: 0 0 0 0.2rem rgba(220,53,69,.15);
        }
        .btn-login {
            background-color: #dc3545;
            border: none;
            border-radius: 8px;
            height: 46px;
            font-size: 16px;
            font-weight: 600;
            width: 100%;
            transition: background-color 0.2s;
        }
        .btn-login:hover { background-color: #bb2d3b; }
        .divider {
            text-align: center;
            color: #aaa;
            font-size: 13px;
            margin: 18px 0;
            position: relative;
        }
        .divider::before, .divider::after {
            content: '';
            position: absolute;
            top: 50%;
            width: 42%;
            height: 1px;
            background: #e0e0e0;
        }
        .divider::before { left: 0; }
        .divider::after  { right: 0; }
        .register-link {
            text-align: center;
            font-size: 14px;
            color: #555;
            margin-top: 20px;
        }
        .register-link a {
            color: #dc3545;
            font-weight: 600;
            text-decoration: none;
        }
        .register-link a:hover { text-decoration: underline; }
        .toggle-password {
            cursor: pointer;
            color: #888;
        }
        .toggle-password:hover { color: #333; }
    </style>
</head>
<body>

    <!-- Header -->
    <header>
        <div class="header-inner">
            <a href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/images/Amazon-Logo.png" alt="Logo" class="logo">
            </a>
            <a href="${pageContext.request.contextPath}/user?action=register">
                <i class="fa-solid fa-user-plus me-1"></i> Đăng ký
            </a>
        </div>
    </header>

    <!-- Form đăng nhập -->
    <div class="login-wrapper">
        <div class="login-card">
            <h2>Đăng nhập</h2>
            <p class="subtitle">Chào mừng bạn trở lại!</p>

            <%-- Thông báo đăng ký thành công --%>
            <% if ("true".equals(request.getParameter("registered"))) { %>
                <div class="alert alert-success alert-dismissible fade show py-2" role="alert">
                    <i class="fa-solid fa-circle-check me-1"></i>
                    Đăng ký thành công! Vui lòng đăng nhập.
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <%-- Thông báo lỗi từ servlet --%>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger alert-dismissible fade show py-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation me-1"></i>
                    ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/user" method="post" novalidate>
                <input type="hidden" name="action" value="login">

                <!-- Email -->
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-control"
                           placeholder="example@email.com"
                           value="${email}"
                           required
                           autofocus>
                </div>

                <!-- Mật khẩu -->
                <div class="mb-4">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <div class="input-group">
                        <input type="password"
                               id="password"
                               name="password"
                               class="form-control"
                               placeholder="Nhập mật khẩu"
                               required>
                        <span class="input-group-text toggle-password" onclick="togglePassword('password', this)">
                            <i class="fa-solid fa-eye"></i>
                        </span>
                    </div>
                </div>

                <button type="submit" class="btn btn-login btn-danger text-white">
                    <i class="fa-solid fa-right-to-bracket me-1"></i> Đăng nhập
                </button>
            </form>

            <div class="divider">hoặc</div>

            <div class="register-link">
                Chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/user?action=register">Đăng ký ngay</a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <script>
        function togglePassword(fieldId, icon) {
            const input = document.getElementById(fieldId);
            const isPassword = input.type === 'password';
            input.type = isPassword ? 'text' : 'password';
            icon.querySelector('i').className = isPassword ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye';
        }
    </script>
</body>
</html>
