package DTO.response;

import java.math.BigDecimal;

import entity.ProductVariant;

public class ProductVariantResponseDTO {
    private long variantId;
    private String size;
    private String color;
    private BigDecimal price;
    private int stock;
    private String sku;

    public static ProductVariantResponseDTO fromEntity(ProductVariant variant) {
        if (variant == null) return null;
        ProductVariantResponseDTO dto = new ProductVariantResponseDTO();
        dto.setVariantId(variant.getVariantId());
        dto.setSize(variant.getSize());
        dto.setColor(variant.getColor());
        dto.setPrice(variant.getPrice());
        dto.setStock(variant.getStock());
        dto.setSku(variant.getSku());
        return dto;
    }

    public long getVariantId() { return variantId; }
    public void setVariantId(long variantId) { this.variantId = variantId; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}
