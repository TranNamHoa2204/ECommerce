package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import entity.Order;
import entity.Payment;
import util.ConnectDB;

public class PaymentDao {

    // 1. Tạo bản ghi thanh toán sau khi đặt hàng
    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO Payment (order_id, method, [status], paid_at) VALUES (?, ?, 'PENDING', NULL)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, payment.getOrder().getOrderId());
            ps.setString(2, payment.getMethod());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Lấy thông tin thanh toán theo Order ID
    public Payment getPaymentByOrderId(long orderId) {
        String sql = "SELECT * FROM Payment WHERE order_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayment(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Cập nhật trạng thái thanh toán (SUCCESS / FAILED)
    public boolean updatePaymentStatus(long orderId, String status) {
        String sql = "UPDATE Payment SET [status] = ?, paid_at = CASE WHEN ? = 'SUCCESS' THEN GETDATE() ELSE paid_at END "
                   + "WHERE order_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, status);
            ps.setLong(3, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper map ResultSet sang đối tượng Payment
    private Payment mapResultSetToPayment(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getLong("order_id"));

        Timestamp ts = rs.getTimestamp("paid_at");
        LocalDateTime paidAt = ts != null ? ts.toLocalDateTime() : null;

        return new Payment(
            rs.getLong("payment_id"),
            order,
            rs.getString("method"),
            rs.getString("status"),
            paidAt
        );
    }
}
