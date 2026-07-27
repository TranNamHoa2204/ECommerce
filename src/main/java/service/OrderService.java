package service;

import java.util.List;

import dao.OrderDao;
import entity.Order;
import entity.OrderDetail;
public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public List<Order> getOrdersByUserId(long userId) {
        return orderDao.getOrdersByUserId(userId);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(long orderId) {
        return orderDao.getOrderDetailsByOrderId(orderId);
    }

    // Tạo đơn hàng đầy đủ kèm chi tiết và trừ tồn kho (dùng transaction)
    public long createOrder(Order order, List<OrderDetail> details) {
        if (order.getUser() == null) {
            throw new RuntimeException("Thông tin người dùng không hợp lệ");
        }
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("Đơn hàng phải có ít nhất một sản phẩm");
        }
        long orderId = orderDao.createOrderWithDetails(order, details);
        if (orderId == 0) {
            throw new RuntimeException("Đặt hàng thất bại, vui lòng thử lại");
        }
        return orderId;
    }

    public boolean cancelOrder(long orderId) {
        return orderDao.cancelOrder(orderId);
    }
}
