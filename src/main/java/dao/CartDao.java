package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import entity.Cart;
import entity.User;
import util.ConnectDB;

public class CartDao {

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

    
}
