package DTO.response;

import entity.ProductImage;

public class ProductImageResponseDTO {
    private long imageId;
    private String color;
    private String imageUrl;
    private int displayOrder;
    private boolean isMain;

    public static ProductImageResponseDTO fromEntity(ProductImage image) {
        if (image == null) return null;
        ProductImageResponseDTO dto = new ProductImageResponseDTO();
        dto.setImageId(image.getImageId());
        dto.setColor(image.getColor());
        dto.setImageUrl(image.getImageUrl());
        dto.setDisplayOrder(image.getDisplayOrder());
        dto.setMain(image.isMain());
        return dto;
    }

    public long getImageId() { return imageId; }
    public void setImageId(long imageId) { this.imageId = imageId; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public boolean isMain() { return isMain; }
    public void setMain(boolean isMain) { this.isMain = isMain; }
}
