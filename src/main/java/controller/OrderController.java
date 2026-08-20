package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.request.CreateOrderRequestDTO;
import DTO.response.OrderDetailResponseDTO;
import DTO.response.OrderResponseDTO;
import entity.Address;
import entity.Order;
import entity.OrderDetail;
import entity.ProductVariant;
import entity.User;
import jakarta.validation.Valid;
import service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Map<String, Long>> createOrder(
            @PathVariable("userId") long userId,
            @Valid @RequestBody CreateOrderRequestDTO request) {

        User user = new User();
        user.setUserId(userId);

        Address address = new Address();
        address.setAddressId(request.getAddressId());

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setShippingFee(request.getShippingFee());
        order.setNote(request.getNote());

        List<OrderDetail> details = new ArrayList<>();
        for (CreateOrderRequestDTO.OrderItemRequestDTO itemDto : request.getItems()) {
            OrderDetail detail = new OrderDetail();
            ProductVariant variant = new ProductVariant();
            variant.setVariantId(itemDto.getVariantId());

            detail.setVariant(variant);
            detail.setQuantity(itemDto.getQuantity());
            detail.setPrice(itemDto.getPrice());
            detail.setSubtotal(itemDto.getPrice().multiply(java.math.BigDecimal.valueOf(itemDto.getQuantity())));
            details.add(detail);
        }

        long orderId = orderService.createOrder(order, details);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("orderId", orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable("userId") long userId) {
        List<OrderResponseDTO> list = orderService.getOrdersByUserId(userId).stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetailsByOrderId(@PathVariable("orderId") long orderId) {
        List<OrderDetailResponseDTO> details = orderService.getOrderDetailsByOrderId(orderId).stream()
                .map(OrderDetailResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(details);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable("orderId") long orderId) {
        boolean success = orderService.cancelOrder(orderId);
        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("error", "Không thể hủy đơn hàng"));
        }
        return ResponseEntity.ok(Map.of("message", "Đã hủy đơn hàng thành công"));
    }
}
