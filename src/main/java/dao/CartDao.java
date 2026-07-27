package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
import entity.CartItem;
import entity.ProductVariant;
import entity.User;
import util.ConnectDB;

public class CartDao {

    // 1. Lấy hoặc tạo giỏ hàng cho User
    public Cart getCartByUserId(long userId) {
        String selectSql = "SELECT * FROM Cart WHERE user_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(userId);

                    Timestamp ts = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;

                    return new Cart(rs.getLong("cart_id"), user, createdAt);
                }
            }

            // Nếu chưa có giỏ hàng, tạo giỏ hàng mới
            return createCartForUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Tạo giỏ hàng mới cho User
    public Cart createCartForUser(long userId) {
        String insertSql = "INSERT INTO Cart (user_id, created_at) VALUES (?, GETDATE())";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long cartId = rs.getLong(1);
                        User user = new User();
                        user.setUserId(userId);
                        return new Cart(cartId, user, LocalDateTime.now());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Lấy danh sách các sản phẩm trong giỏ hàng (CartItem)
    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT ci.*, v.size, v.color, v.price, v.stock, v.sku "
                + "FROM CartItem ci "
                + "INNER JOIN ProductVariant v ON ci.variant_id = v.variant_id "
                + "WHERE ci.cart_id = ?";

        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(cartId);

                    ProductVariant variant = new ProductVariant(
                            rs.getLong("variant_id"),
                            null,
                            rs.getString("size"),
                            rs.getNString("color"),
                            rs.getBigDecimal("price"),
                            rs.getInt("stock"),
                            rs.getString("sku"));

                    CartItem item = new CartItem(
                            rs.getLong("cart_item_id"),
                            cart,
                            variant,
                            rs.getInt("quantity"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Thêm sản phẩm biến thể vào giỏ hàng (Cập nhật số lượng nếu đã tồn tại)
    public boolean addOrUpdateCartItem(long cartId, long variantId, int quantity) {
        String checkSql = "SELECT cart_item_id, quantity FROM CartItem WHERE cart_id = ? AND variant_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setLong(1, cartId);
            psCheck.setLong(2, variantId);

            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next()) {
                    long cartItemId = rs.getLong("cart_item_id");
                    int oldQty = rs.getInt("quantity");
                    String updateSql = "UPDATE CartItem SET quantity = ? WHERE cart_item_id = ?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setInt(1, oldQty + quantity);
                        psUpdate.setLong(2, cartItemId);
                        return psUpdate.executeUpdate() > 0;
                    }
                } else {
                    String insertSql = "INSERT INTO CartItem (cart_id, variant_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                        psInsert.setLong(1, cartId);
                        psInsert.setLong(2, variantId);
                        psInsert.setInt(3, quantity);
                        return psInsert.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa sản phẩm khỏi giỏ hàng
    public boolean deleteCartItem(long cartItemId) {
        String sql = "DELETE FROM CartItem WHERE cart_item_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
