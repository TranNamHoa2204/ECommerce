package DTO.response;

import entity.Brand;

public class BrandResponseDTO {
    private long brandId;
    private String name;

    public static BrandResponseDTO fromEntity(Brand brand) {
        if (brand == null) return null;
        BrandResponseDTO dto = new BrandResponseDTO();
        dto.setBrandId(brand.getBrandId());
        dto.setName(brand.getName());
        return dto;
    }

    public long getBrandId() { return brandId; }
    public void setBrandId(long brandId) { this.brandId = brandId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
