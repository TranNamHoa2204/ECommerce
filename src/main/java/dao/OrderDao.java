package dao;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Address;
import entity.Order;
import entity.OrderDetail;
import entity.ProductVariant;
import entity.User;
import util.ConnectDB;

public class OrderDao {

    // 1. Tạo đơn hàng (Được gọi từ OrderService)
    public boolean createOrder(Order order) {
        String sql = "INSERT INTO [Order] (user_id, address_id, total_amount, shipping_fee, note, [status], created_at) "
                   + "VALUES (?, ?, ?, ?, ?, 'PENDING', GETDATE())";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, order.getUser() != null ? order.getUser().getUserId() : 0);
            if (order.getAddress() != null) {
                ps.setLong(2, order.getAddress().getAddressId());
            } else {
                ps.setNull(2, java.sql.Types.BIGINT);
            }
            ps.setBigDecimal(3, order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO);
            ps.setBigDecimal(4, order.getShippingFee() != null ? order.getShippingFee() : BigDecimal.ZERO);
            ps.setNString(5, order.getNote());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Tạo đơn hàng đầy đủ kèm các OrderDetail và trừ kho (Transaction DB)
    public long createOrderWithDetails(Order order, List<OrderDetail> details) {
        String insertOrderSql = "INSERT INTO [Order] (user_id, address_id, total_amount, shipping_fee, note, [status], created_at) "
                              + "VALUES (?, ?, ?, ?, ?, 'PENDING', GETDATE())";
        String insertDetailSql = "INSERT INTO OrderDetail (order_id, variant_id, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
        String updateStockSql = "UPDATE ProductVariant SET stock = stock - ? WHERE variant_id = ? AND stock >= ?";

        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            long orderId = 0;
            try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psOrder.setLong(1, order.getUser().getUserId());
                if (order.getAddress() != null) {
                    psOrder.setLong(2, order.getAddress().getAddressId());
                } else {
                    psOrder.setNull(2, java.sql.Types.BIGINT);
                }
                psOrder.setBigDecimal(3, order.getTotalAmount());
                psOrder.setBigDecimal(4, order.getShippingFee());
                psOrder.setNString(5, order.getNote());

                int affected = psOrder.executeUpdate();
                if (affected > 0) {
                    try (ResultSet rs = psOrder.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getLong(1);
                        }
                    }
                }
            }

            if (orderId == 0) {
                conn.rollback();
                return 0;
            }

            // Chèn OrderDetail & trừ tồn kho Variant
            try (PreparedStatement psDetail = conn.prepareStatement(insertDetailSql);
                 PreparedStatement psStock = conn.prepareStatement(updateStockSql)) {
                for (OrderDetail d : details) {
                    psDetail.setLong(1, orderId);
                    psDetail.setLong(2, d.getVariant().getVariantId());
                    psDetail.setBigDecimal(3, d.getPrice());
                    psDetail.setInt(4, d.getQuantity());
                    psDetail.setBigDecimal(5, d.getSubtotal());
                    psDetail.addBatch();

                    psStock.setInt(1, d.getQuantity());
                    psStock.setLong(2, d.getVariant().getVariantId());
                    psStock.setInt(3, d.getQuantity());
                    psStock.addBatch();
                }
                psDetail.executeBatch();
                psStock.executeBatch();
            }

            conn.commit(); // Commit Transaction
            return orderId;

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return 0;
    }

    // 3. Hủy đơn hàng (Được gọi từ OrderService)
    public boolean cancelOrder(long orderId) {
        String sql = "UPDATE [Order] SET status = 'CANCELLED' WHERE order_id = ? AND status IN ('PENDING', 'PROCESSING')";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Lấy danh sách đơn hàng của một người dùng
    public List<Order> getOrdersByUserId(long userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(userId);

                    Address addr = null;
                    long addrId = rs.getLong("address_id");
                    if (addrId > 0) {
                        addr = new Address();
                        addr.setAddressId(addrId);
                    }

                    Timestamp ts = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;

                    Order o = new Order(
                        rs.getLong("order_id"),
                        u,
                        addr,
                        rs.getBigDecimal("total_amount"),
                        rs.getBigDecimal("shipping_fee"),
                        rs.getNString("note"),
                        rs.getString("status"),
                        createdAt
                    );
                    list.add(o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 5. Lấy chi tiết đơn hàng (OrderDetail) theo Order ID
    public List<OrderDetail> getOrderDetailsByOrderId(long orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT od.*, v.size, v.color, v.sku, p.name AS product_name "
                   + "FROM OrderDetail od "
                   + "INNER JOIN ProductVariant v ON od.variant_id = v.variant_id "
                   + "INNER JOIN [Product] p ON v.product_id = p.product_id "
                   + "WHERE od.order_id = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(orderId);

                    ProductVariant variant = new ProductVariant(
                        rs.getLong("variant_id"),
                        null,
                        rs.getString("size"),
                        rs.getNString("color"),
                        rs.getBigDecimal("price"),
                        0,
                        rs.getString("sku")
                    );

                    OrderDetail detail = new OrderDetail(
                        rs.getLong("order_detail_id"),
                        order,
                        variant,
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("subtotal")
                    );
                    list.add(detail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
