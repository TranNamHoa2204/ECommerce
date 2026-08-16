package DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PaymentRequestDTO {

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Pattern(
        regexp = "^(COD|VNPAY|BANK_TRANSFER)$",
        message = "Phương thức thanh toán phải là COD, VNPAY hoặc BANK_TRANSFER"
    )
    private String method;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
