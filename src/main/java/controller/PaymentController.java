package controller;

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

import DTO.request.PaymentRequestDTO;
import DTO.response.PaymentResponseDTO;
import entity.Payment;
import jakarta.validation.Valid;
import service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @PathVariable("orderId") long orderId,
            @Valid @RequestBody PaymentRequestDTO request) {

        Payment payment = paymentService.createPayment(orderId, request.getMethod());
        return ResponseEntity.status(HttpStatus.CREATED).body(PaymentResponseDTO.fromEntity(payment));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable("orderId") long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }

    @PutMapping("/order/{orderId}/success")
    public ResponseEntity<PaymentResponseDTO> markPaymentSuccess(@PathVariable("orderId") long orderId) {
        Payment payment = paymentService.markPaymentSuccess(orderId);
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }

    @PutMapping("/order/{orderId}/failed")
    public ResponseEntity<PaymentResponseDTO> markPaymentFailed(@PathVariable("orderId") long orderId) {
        Payment payment = paymentService.markPaymentFailed(orderId);
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }

    @GetMapping("/order/{orderId}/status")
    public ResponseEntity<Map<String, Boolean>> isPaymentSuccessful(@PathVariable("orderId") long orderId) {
        boolean isSuccess = paymentService.isPaymentSuccessful(orderId);
        return ResponseEntity.ok(Map.of("isSuccess", isSuccess));
    }

    @PutMapping("/order/{orderId}/method")
    public ResponseEntity<PaymentResponseDTO> updatePaymentMethod(
            @PathVariable("orderId") long orderId,
            @Valid @RequestBody PaymentRequestDTO request) {

        Payment payment = paymentService.updatePaymentMethod(orderId, request.getMethod());
        return ResponseEntity.ok(PaymentResponseDTO.fromEntity(payment));
    }
}
