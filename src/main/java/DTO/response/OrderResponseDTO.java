package DTO.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import entity.Order;

public class OrderResponseDTO {
    private long orderId;
    private long userId;
    private String userName;
    // Flatten address — chỉ expose thông tin cần thiết
    private Long addressId;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    // Danh sách chi tiết đơn hàng (nếu cần)
    private List<OrderDetailResponseDTO> details;

    public static OrderResponseDTO fromEntity(Order order) {
        if (order == null) return null;
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getUserId());
            dto.setUserName(order.getUser().getFullName());
        }
        if (order.getAddress() != null) {
            dto.setAddressId(order.getAddress().getAddressId());
            // Ghép địa chỉ giao hàng thành chuỗi readable
            dto.setShippingAddress(
                order.getAddress().getDetailAddress() + ", " +
                order.getAddress().getWard() + ", " +
                order.getAddress().getDistrict() + ", " +
                order.getAddress().getProvince()
            );
        }
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingFee(order.getShippingFee());
        dto.setNote(order.getNote());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderDetailResponseDTO> getDetails() { return details; }
    public void setDetails(List<OrderDetailResponseDTO> details) { this.details = details; }
}
