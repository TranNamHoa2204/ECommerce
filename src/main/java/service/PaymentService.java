package service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Order;
import entity.Payment;
import respository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    
    public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

    // Tạo bản ghi thanh toán sau khi đặt hàng thành công
    @Transactional
    public Payment createPayment(long orderId, String method) {
    	
        if (method == null || (!method.equals("COD") && !method.equals("VNPAY") && !method.equals("BANK_TRANSFER"))) {
            throw new RuntimeException("Phương thức thanh toán không hợp lệ");
        }
        
        if(paymentRepository.findByOrderOrderId(orderId).isPresent()) {
			throw new RuntimeException("Đã tồn tại bản ghi thanh toán cho Order ID: " + orderId);
		}

        Order order = new Order();
        order.setOrderId(orderId);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(method);
        payment.setStatus("PENDING");
        payment.setPaidAt(null);
        
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(long orderId) {
        Payment payment = paymentRepository.findByOrderOrderId(orderId).orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin thanh toán cho Order ID: " + orderId));
        return payment;
    }

    @Transactional
    public Payment markPaymentSuccess(long orderId) {
    	Payment payment = getPaymentByOrderId(orderId);
    	if(!payment.getStatus().equals("PENDING")) {
    		throw new RuntimeException("Chỉ có thể cập nhật thanh toán từ trạng thái PENDING");
    	}
    	payment.setStatus("SUCCESS");
    	payment.setPaidAt(java.time.LocalDateTime.now());
    	
    	return paymentRepository.save(payment);
    }

    @Transactional
    public Payment markPaymentFailed(long orderId) {
    	Payment payment = getPaymentByOrderId(orderId);
    	if(!payment.getStatus().equals("PENDING")) {
    		throw new RuntimeException("Chỉ có thể cập nhật thanh toán từ trạng thái PENDING");
    	}
    	payment.setStatus("FAILED");
    	payment.setPaidAt(java.time.LocalDateTime.now());
    	
    	return paymentRepository.save(payment);
    }
    
    // Kiểm tra trạng thái thanh toán của đơn hàng
    public boolean isPaymentSuccessful(long orderId) {
        return paymentRepository.findByOrderOrderId(orderId)
            .map(payment -> payment.getStatus().equals("SUCCESS"))
            .orElse(false);
    }
    
    // Cập nhật phương thức thanh toán (nếu cần)
    @Transactional
    public Payment updatePaymentMethod(long orderId, String newMethod) {
        // Validate phương thức thanh toán mới
        if (newMethod == null || (!newMethod.equals("COD") && !newMethod.equals("VNPAY") && !newMethod.equals("BANK_TRANSFER"))) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
        }
        
        Payment payment = getPaymentByOrderId(orderId);
        
        if (payment.getStatus().equals("SUCCESS") || payment.getStatus().equals("FAILED")) {
            throw new RuntimeException("Không thể thay đổi phương thức thanh toán khi đã xác nhận hoặc thất bại");
        }
        
        payment.setMethod(newMethod);
        return paymentRepository.save(payment);
    }
}
