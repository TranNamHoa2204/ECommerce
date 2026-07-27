package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Product;
import entity.Review;
import entity.User;
import util.ConnectDB;

public class ReviewDao {

    // 1. Lấy tất cả đánh giá của một sản phẩm
    public List<Review> getReviewsByProductId(long productId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name "
                   + "FROM Review r "
                   + "INNER JOIN [User] u ON r.user_id = u.user_id "
                   + "WHERE r.product_id = ? "
                   + "ORDER BY r.created_at DESC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToReview(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm đánh giá mới
    public boolean insertReview(Review review) {
        String sql = "INSERT INTO Review (user_id, product_id, rating, comment, created_at) "
                   + "VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, review.getUser().getUserId());
            ps.setLong(2, review.getProduct().getProductId());
            ps.setInt(3, review.getRating());
            ps.setNString(4, review.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Kiểm tra user đã đánh giá sản phẩm này chưa
    public boolean hasUserReviewed(long userId, long productId) {
        String sql = "SELECT COUNT(*) FROM Review WHERE user_id = ? AND product_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Kiểm tra user đã mua sản phẩm này chưa (qua OrderDetail)
    public boolean hasUserPurchasedProduct(long userId, long productId) {
        String sql = "SELECT COUNT(*) FROM OrderDetail od "
                   + "INNER JOIN [Order] o ON od.order_id = o.order_id "
                   + "INNER JOIN ProductVariant v ON od.variant_id = v.variant_id "
                   + "WHERE o.user_id = ? AND v.product_id = ? AND o.[status] = 'DELIVERED'";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Tính điểm trung bình của sản phẩm
    public double getAverageRating(long productId) {
        String sql = "SELECT AVG(CAST(rating AS FLOAT)) FROM Review WHERE product_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Helper map ResultSet sang đối tượng Review
    private Review mapResultSetToReview(ResultSet rs) throws Exception {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setFullName(rs.getNString("full_name"));

        Product product = new Product();
        product.setProductId(rs.getLong("product_id"));

        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;

        return new Review(
            rs.getLong("review_id"),
            user,
            product,
            rs.getInt("rating"),
            rs.getNString("comment"),
            createdAt
        );
    }
}
