<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

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

        .register-wrapper {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 16px;
        }
        .register-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px 36px;
            width: 100%;
            max-width: 480px;
        }
        .register-card h2 {
            font-size: 24px;
            font-weight: 700;
            color: #222;
            margin-bottom: 6px;
        }
        .register-card .subtitle {
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
        .form-control.is-invalid:focus {
            box-shadow: 0 0 0 0.2rem rgba(220,53,69,.25);
        }
        .btn-register {
            background-color: #dc3545;
            border: none;
            border-radius: 8px;
            height: 46px;
            font-size: 16px;
            font-weight: 600;
            width: 100%;
            transition: background-color 0.2s;
        }
        .btn-register:hover { background-color: #bb2d3b; }
        .login-link {
            text-align: center;
            font-size: 14px;
            color: #555;
            margin-top: 20px;
        }
        .login-link a {
            color: #dc3545;
            font-weight: 600;
            text-decoration: none;
        }
        .login-link a:hover { text-decoration: underline; }
        .toggle-password {
            cursor: pointer;
            color: #888;
        }
        .toggle-password:hover { color: #333; }
        .password-hint {
            font-size: 12px;
            color: #aaa;
            margin-top: 4px;
        }
    </style>
</head>
<body>

    <!-- Header -->
    <header>
        <div class="header-inner">
            <a href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/images/Amazon-Logo.png" alt="Logo" class="logo">
            </a>
            <a href="${pageContext.request.contextPath}/user?action=login">
                <i class="fa-solid fa-right-to-bracket me-1"></i> Đăng nhập
            </a>
        </div>
    </header>

    <!-- Form đăng ký -->
    <div class="register-wrapper">
        <div class="register-card">
            <h2>Tạo tài khoản</h2>
            <p class="subtitle">Đăng ký để mua sắm dễ dàng hơn</p>

            <%-- Thông báo lỗi từ servlet --%>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger alert-dismissible fade show py-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation me-1"></i>
                    ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/user" method="post" novalidate id="registerForm">
                <input type="hidden" name="action" value="register">

                <!-- Họ và tên -->
                <div class="mb-3">
                    <label for="fullName" class="form-label">Họ và tên</label>
                    <input type="text"
                           id="fullName"
                           name="fullName"
                           class="form-control"
                           placeholder="Nguyễn Văn A"
                           value="${fullName}"
                           required
                           autofocus>
                </div>

                <!-- Email -->
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-control"
                           placeholder="example@email.com"
                           value="${email}"
                           required>
                </div>

                <!-- Số điện thoại -->
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel"
                           id="phone"
                           name="phone"
                           class="form-control"
                           placeholder="0xxxxxxxxx"
                           value="${phone}"
                           required>
                </div>

                <!-- Mật khẩu -->
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <div class="input-group">
                        <input type="password"
                               id="password"
                               name="password"
                               class="form-control"
                               placeholder="Tối thiểu 6 ký tự"
                               required>
                        <span class="input-group-text toggle-password" onclick="togglePassword('password', this)">
                            <i class="fa-solid fa-eye"></i>
                        </span>
                    </div>
                    <div class="password-hint">Tối thiểu 6 ký tự</div>
                </div>

                <!-- Xác nhận mật khẩu -->
                <div class="mb-4">
                    <label for="repassword" class="form-label">Xác nhận mật khẩu</label>
                    <div class="input-group">
                        <input type="password"
                               id="repassword"
                               name="repassword"
                               class="form-control"
                               placeholder="Nhập lại mật khẩu"
                               required>
                        <span class="input-group-text toggle-password" onclick="togglePassword('repassword', this)">
                            <i class="fa-solid fa-eye"></i>
                        </span>
                    </div>
                    <div id="repasswordError" class="invalid-feedback d-block" style="display:none!important;"></div>
                </div>

                <button type="submit" class="btn btn-register text-white">
                    <i class="fa-solid fa-user-plus me-1"></i> Đăng ký
                </button>
            </form>

            <div class="login-link">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/user?action=login">Đăng nhập</a>
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

        // Validate confirm password phía client trước khi submit
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            const pw  = document.getElementById('password').value;
            const rpw = document.getElementById('repassword').value;
            const errDiv = document.getElementById('repasswordError');

            if (pw !== rpw) {
                e.preventDefault();
                document.getElementById('repassword').classList.add('is-invalid');
                errDiv.textContent = 'Mật khẩu xác nhận không khớp';
                errDiv.style.display = 'block';
            } else {
                document.getElementById('repassword').classList.remove('is-invalid');
                errDiv.style.display = 'none';
            }
        });

        // Xoá trạng thái lỗi khi người dùng gõ lại
        document.getElementById('repassword').addEventListener('input', function() {
            this.classList.remove('is-invalid');
            document.getElementById('repasswordError').style.display = 'none';
        });
    </script>
</body>
</html>
