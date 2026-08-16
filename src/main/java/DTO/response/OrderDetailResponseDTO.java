package DTO.response;

import java.math.BigDecimal;

import entity.OrderDetail;

public class OrderDetailResponseDTO {
    private long orderDetailId;
    private long variantId;
    private String productName;
    private String size;
    private String color;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;

    public static OrderDetailResponseDTO fromEntity(OrderDetail detail) {
        if (detail == null) return null;
        OrderDetailResponseDTO dto = new OrderDetailResponseDTO();
        dto.setOrderDetailId(detail.getOrderDetailId());
        if (detail.getVariant() != null) {
            dto.setVariantId(detail.getVariant().getVariantId());
            dto.setSize(detail.getVariant().getSize());
            dto.setColor(detail.getVariant().getColor());
            if (detail.getVariant().getProduct() != null) {
                dto.setProductName(detail.getVariant().getProduct().getName());
            }
        }
        dto.setPrice(detail.getPrice());
        dto.setQuantity(detail.getQuantity());
        dto.setSubtotal(detail.getSubtotal());
        return dto;
    }

    public long getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(long orderDetailId) { this.orderDetailId = orderDetailId; }
    public long getVariantId() { return variantId; }
    public void setVariantId(long variantId) { this.variantId = variantId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
