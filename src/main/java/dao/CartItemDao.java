package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Cart;
import entity.CartItem;
import entity.ProductVariant;
import util.ConnectDB;

public class CartItemDao {

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

    // Thêm sản phẩm biến thể vào giỏ hàng (Cập nhật số lượng nếu đã tồn tại)
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

    // Cập nhật số lượng trực tiếp (dùng khi user nhập số lượng mới trong giỏ)
    public boolean updateQuantity(long cartItemId, int newQuantity) {
        String sql = "UPDATE CartItem SET quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setLong(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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

    // Xóa toàn bộ item trong giỏ (dùng sau khi đặt hàng thành công)
    public boolean clearCart(long cartId) {
        String sql = "DELETE FROM CartItem WHERE cart_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
