package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entity.Address;
import entity.Cart;
import entity.CartItem;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AddressService;
import service.CartItemService;
import service.CartService;
import service.OrderService;
import service.PaymentService;

/**
 * GET  /order                         → trang đặt hàng (chọn địa chỉ, phương thức thanh toán)
 * GET  /order?action=history          → lịch sử đơn hàng của user
 * GET  /order?action=detail&orderId=  → chi tiết một đơn hàng
 * POST /order?action=place            → xác nhận đặt hàng
 * POST /order?action=cancel&orderId=  → hủy đơn hàng
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService         = new OrderService();
    private final CartService cartService           = new CartService();
    private final CartItemService cartItemService   = new CartItemService();
    private final AddressService addressService     = new AddressService();
    private final PaymentService paymentService     = new PaymentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "history" -> showOrderHistory(req, resp);
            case "detail"  -> showOrderDetail(req, resp);
            default        -> showCheckoutPage(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "place"  -> handlePlaceOrder(req, resp);
            case "cancel" -> handleCancelOrder(req, resp);
            default       -> resp.sendRedirect(req.getContextPath() + "/order");
        }
    }

    // Trang checkout: hiển thị giỏ hàng + danh sách địa chỉ để chọn
    private void showCheckoutPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            Cart cart = cartService.getCartByUserId(currentUser.getUserId());
            List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cart.getCartId());

            if (cartItems.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }

            List<Address> addresses = addressService.getAddressesByUserId(currentUser.getUserId());

            req.setAttribute("cartItems", cartItems);
            req.setAttribute("addresses", addresses);
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
        }
    }

    private void showOrderHistory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        List<Order> orders = orderService.getOrdersByUserId(currentUser.getUserId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orderHistory.jsp").forward(req, resp);
    }

    private void showOrderDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long orderId = parseLong(req.getParameter("orderId"));
            List<OrderDetail> details = orderService.getOrderDetailsByOrderId(orderId);

            req.setAttribute("orderId", orderId);
            req.setAttribute("orderDetails", details);
            req.getRequestDispatcher("/WEB-INF/views/orderDetail.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Đặt hàng: tạo Order + OrderDetail + Payment, xóa giỏ hàng
    private void handlePlaceOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long addressId      = parseLong(req.getParameter("addressId"));
            String paymentMethod = req.getParameter("paymentMethod");
            String note          = req.getParameter("note");

            // Lấy địa chỉ (kèm kiểm tra quyền sở hữu)
            Address address = addressService.getAddressById(addressId, currentUser.getUserId());

            // Lấy giỏ hàng
            Cart cart = cartService.getCartByUserId(currentUser.getUserId());
            List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cart.getCartId());

            if (cartItems.isEmpty()) {
                throw new RuntimeException("Giỏ hàng trống, không thể đặt hàng");
            }

            // Tính toán tổng tiền và build OrderDetail
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal shippingFee = calculateShippingFee(totalAmount);
            List<OrderDetail> details = new ArrayList<>();

            for (CartItem item : cartItems) {
                BigDecimal price    = item.getVariant().getPrice();
                int qty             = item.getQuantity();
                BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));
                totalAmount         = totalAmount.add(subtotal);

                OrderDetail detail = new OrderDetail();
                detail.setVariant(item.getVariant());
                detail.setPrice(price);
                detail.setQuantity(qty);
                detail.setSubtotal(subtotal);
                details.add(detail);
            }

            shippingFee = calculateShippingFee(totalAmount);

            // Build Order
            Order order = new Order();
            order.setUser(currentUser);
            order.setAddress(address);
            order.setTotalAmount(totalAmount.add(shippingFee));
            order.setShippingFee(shippingFee);
            order.setNote(note);

            // Tạo đơn hàng (transaction: insert Order + OrderDetails + giảm kho)
            long orderId = orderService.createOrder(order, details);

            // Tạo bản ghi thanh toán
            paymentService.createPayment(orderId, paymentMethod);

            // Xóa giỏ hàng sau khi đặt hàng thành công
            cartItemService.clearCart(cart.getCartId());

            // Redirect đến trang xác nhận / thanh toán
            resp.sendRedirect(req.getContextPath() + "/payment?action=confirm&orderId=" + orderId);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showCheckoutPage(req, resp);
        }
    }

    private void handleCancelOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long orderId = parseLong(req.getParameter("orderId"));
            boolean success = orderService.cancelOrder(orderId);

            if (!success) {
                req.setAttribute("errorMessage", "Không thể hủy đơn hàng (chỉ hủy được khi đang PENDING hoặc PROCESSING)");
                showOrderHistory(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/order?action=history");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showOrderHistory(req, resp);
        }
    }

    // Tính phí ship đơn giản (có thể mở rộng theo địa chỉ / tổng tiền)
    private BigDecimal calculateShippingFee(BigDecimal totalAmount) {
        // Miễn phí ship nếu đơn >= 500.000 VND
        if (totalAmount.compareTo(new BigDecimal("500000")) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("30000");
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
