package controller;

import java.io.IOException;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserService;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "login"    -> req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            case "register" -> req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            case "logout"   -> {
                HttpSession session = req.getSession(false);
                if (session != null) session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/");
            }
            default         -> resp.sendRedirect(req.getContextPath() + "/");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "login"    -> handleLogin(req, resp);
            case "register" -> handleRegister(req, resp);
            default         -> resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.dangNhap(email, password);

            // Lưu thông tin user vào session
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            // Admin về trang quản trị, khách hàng về trang chủ
            if ("ADMIN".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }

        } catch (RuntimeException e) {
            // Đưa thông báo lỗi về lại trang login
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("email", email); // giữ lại email đã nhập
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fullName  = req.getParameter("fullName");
        String email     = req.getParameter("email");
        String password  = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        String phone     = req.getParameter("phone");

        // Kiểm tra mật khẩu xác nhận ngay tại Controller
        if (!password.equals(repassword)) {
            req.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
            req.setAttribute("fullName", fullName);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            userService.dangKy(fullName, email, password, phone);

            resp.sendRedirect(req.getContextPath() + "/user?action=login&registered=true");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("fullName", fullName);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
