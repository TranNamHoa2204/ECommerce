package DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import entity.Product;

public class ProductResponseDTO {
    private long productId;
    // Flatten category & brand thành id + name — tránh vòng lặp
    private long categoryId;
    private String categoryName;
    private long brandId;
    private String brandName;
    private String name;
    private String description;
    private boolean status;
    private LocalDateTime createdAt;
    // Danh sách biến thể & ảnh (nếu truy vấn có kèm theo)
    private List<ProductVariantResponseDTO> variants;
    private List<ProductImageResponseDTO> images;

    public static ProductResponseDTO fromEntity(Product product) {
        if (product == null)
            return null;
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(product.getProductId());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getBrand() != null) {
            dto.setBrandId(product.getBrand().getBrandId());
            dto.setBrandName(product.getBrand().getName());
        }
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus());
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProductVariantResponseDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantResponseDTO> variants) {
        this.variants = variants;
    }

    public List<ProductImageResponseDTO> getImages() {
        return images;
    }

    public void setImages(List<ProductImageResponseDTO> images) {
        this.images = images;
    }
}
