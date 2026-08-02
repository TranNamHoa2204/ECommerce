package controller;

import java.io.IOException;
import java.util.List;

import entity.OrderDetail;
import entity.Payment;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.OrderService;
import service.PaymentService;

/**
 * GET  /payment?action=confirm&orderId=  → trang xác nhận đơn hàng sau khi đặt
 * POST /payment?action=complete          → COD: đánh dấu PENDING (chờ giao)
 * POST /payment?action=success           → callback thành công (VNPAY / BANK_TRANSFER)
 * POST /payment?action=failed            → callback thất bại
 */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private final PaymentService paymentService = new PaymentService();
    private final OrderService orderService     = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "confirm" -> showConfirmPage(req, resp);
            default        -> resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "complete" -> handleComplete(req, resp);  // COD: giữ PENDING
            case "success"  -> handleSuccess(req, resp);   // VNPAY / bank callback thành công
            case "failed"   -> handleFailed(req, resp);    // VNPAY / bank callback thất bại
            default         -> resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    // Hiển thị: thông tin payment, danh sách sản phẩm đã đặt
    private void showConfirmPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long orderId = parseLong(req.getParameter("orderId"));

            Payment payment = paymentService.getPaymentByOrderId(orderId);
            List<OrderDetail> details = orderService.getOrderDetailsByOrderId(orderId);

            req.setAttribute("payment", payment);
            req.setAttribute("orderDetails", details);
            req.getRequestDispatcher("/WEB-INF/views/paymentConfirm.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/paymentConfirm.jsp").forward(req, resp);
        }
    }

    // COD: người dùng bấm "Xác nhận" → thanh toán khi nhận hàng, giữ PENDING
    private void handleComplete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long orderId = parseLong(req.getParameter("orderId"));

            // Với COD, Payment giữ PENDING cho đến khi giao hàng thành công
            // Chỉ cần forward sang trang cảm ơn / lịch sử đơn hàng
            req.setAttribute("successMessage", "Đặt hàng thành công! Chúng tôi sẽ liên hệ xác nhận sớm.");
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/WEB-INF/views/orderSuccess.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/order?action=history");
        }
    }

    // VNPAY / BANK_TRANSFER callback: thanh toán thành công
    // POST params: orderId
    // (Thực tế VNPAY sẽ gọi GET callback với query params — mở rộng sau)
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            long orderId = parseLong(req.getParameter("orderId"));
            paymentService.markPaymentSuccess(orderId);

            req.setAttribute("successMessage", "Thanh toán thành công!");
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/WEB-INF/views/orderSuccess.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/order?action=history");
        }
    }

    // VNPAY / BANK_TRANSFER callback: thanh toán thất bại
    // POST params: orderId
    private void handleFailed(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            long orderId = parseLong(req.getParameter("orderId"));
            paymentService.markPaymentFailed(orderId);

            req.setAttribute("errorMessage", "Thanh toán thất bại. Bạn có thể thử lại hoặc chọn COD.");
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/WEB-INF/views/paymentConfirm.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/order?action=history");
        }
    }

    private User getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("currentUser");
    }

    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Tham số không hợp lệ");
        }
    }
}
