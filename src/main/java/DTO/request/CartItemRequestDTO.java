package DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartItemRequestDTO {

    @NotNull(message = "Variant ID không được để trống")
    private Long variantId;

    @Positive(message = "Số lượng phải lớn hơn 0")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    private int quantity;

    public Long getVariantId() { return variantId; }
    public void setVariantId(Long variantId) { this.variantId = variantId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
