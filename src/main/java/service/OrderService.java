package service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Order;
import entity.OrderDetail;
import entity.ProductVariant;
import respository.OrderDetailRepository;
import respository.OrderRepository;
import respository.ProductVariantRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductVariantRepository productVariantRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        ProductVariantRepository productVariantRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    // Tạo đơn hàng đầy đủ kèm chi tiết và trừ tồn kho (dùng transaction)
    @Transactional
    public long createOrder(Order order, List<OrderDetail> details) {
        if (order.getUser() == null) {
            throw new RuntimeException("Thông tin người dùng không hợp lệ");
        }
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("Đơn hàng phải có ít nhất một sản phẩm");
        }

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PENDING");
        }
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }

        // 1. Giảm số lượng tồn kho của từng biến thể
        for (OrderDetail detail : details) {
            ProductVariant variant = productVariantRepository.findById(detail.getVariant().getVariantId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể sản phẩm"));

            if (variant.getStock() < detail.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + (variant.getProduct() != null ? variant.getProduct().getName() : "") + " không đủ tồn kho");
            }
            variant.setStock(variant.getStock() - detail.getQuantity());
            productVariantRepository.save(variant);
        }

        // 2. Lưu Order
        Order savedOrder = orderRepository.save(order);

        // 3. Lưu danh sách OrderDetail
        for (OrderDetail detail : details) {
            detail.setOrder(savedOrder);
            orderDetailRepository.save(detail);
        }

        return savedOrder.getOrderId();
    }

    @Transactional
    public boolean cancelOrder(long orderId) {
        int updatedCount = orderRepository.cancelOrder(orderId);
        return updatedCount > 0;
    }
}
