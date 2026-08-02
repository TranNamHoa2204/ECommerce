package controller;

import java.io.IOException;
import java.util.List;

import entity.Cart;
import entity.CartItem;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CartItemService;
import service.CartService;

/**
 * GET  /cart                       → hiển thị giỏ hàng
 * POST /cart?action=add            → thêm/cộng dồn sản phẩm vào giỏ
 * POST /cart?action=update         → cập nhật số lượng một item
 * POST /cart?action=remove         → xóa một item khỏi giỏ
 * POST /cart?action=clear          → xóa toàn bộ giỏ hàng
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();
    private final CartItemService cartItemService = new CartItemService();

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        showCartList(req, resp);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add"    -> handleAdd(req, resp);
            case "update" -> handleUpdate(req, resp);
            case "remove" -> handleRemove(req, resp);
            case "clear"  -> handleClear(req, resp);
            default       -> resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    
    private void showCartList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            Cart cart = cartService.getCartByUserId(currentUser.getUserId());
            List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cart.getCartId());

            req.setAttribute("cart", cart);
            req.setAttribute("cartItems", cartItems);
            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
        }
    }
   
    // POST params: variantId, quantity
    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long variantId = parseLong(req.getParameter("variantId"));
            int quantity   = parseInt(req.getParameter("quantity"), 1);

            Cart cart = cartService.getCartByUserId(currentUser.getUserId());
            cartItemService.addOrUpdateCartItem(cart.getCartId(), variantId, quantity);

            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showCartList(req, resp);
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long cartItemId = parseLong(req.getParameter("cartItemId"));
            int quantity    = parseInt(req.getParameter("quantity"), 1);

            cartItemService.updateQuantity(cartItemId, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showCartList(req, resp);
        }
    }

    private void handleRemove(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            long cartItemId = parseLong(req.getParameter("cartItemId"));
            cartItemService.deleteCartItem(cartItemId);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showCartList(req, resp);
        }
    }

    private void handleClear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = getLoginUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        try {
            Cart cart = cartService.getCartByUserId(currentUser.getUserId());
            cartItemService.clearCart(cart.getCartId());
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            showCartList(req, resp);
        }
    }

    // Helper: lấy user từ session
    private User getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("currentUser");
    }

    // Helper: parse param an toàn
    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Tham số không hợp lệ");
        }
    }

    private int parseInt(String value, int defaultValue) {
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Số lượng không hợp lệ");
        }
    }
}
