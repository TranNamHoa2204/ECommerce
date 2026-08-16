package DTO.response;

import java.math.BigDecimal;

import entity.CartItem;

public class CartItemResponseDTO {
    private long cartItemId;
    private long variantId;
    private String productName;
    private String size;
    private String color;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;

    public static CartItemResponseDTO fromEntity(CartItem item) {
        if (item == null) return null;
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartItemId(item.getCartItemId());
        if (item.getVariant() != null) {
            dto.setVariantId(item.getVariant().getVariantId());
            dto.setSize(item.getVariant().getSize());
            dto.setColor(item.getVariant().getColor());
            dto.setPrice(item.getVariant().getPrice());
            if (item.getVariant().getProduct() != null) {
                dto.setProductName(item.getVariant().getProduct().getName());
            }
        }
        dto.setQuantity(item.getQuantity());
        // Tính subtotal tại DTO layer
        if (item.getVariant() != null && item.getVariant().getPrice() != null) {
            dto.setSubtotal(item.getVariant().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return dto;
    }

    public long getCartItemId() { return cartItemId; }
    public void setCartItemId(long cartItemId) { this.cartItemId = cartItemId; }
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
