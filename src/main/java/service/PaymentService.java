package service;

import dao.PaymentDao;
import entity.Order;
import entity.Payment;

public class PaymentService {
    private PaymentDao paymentDao = new PaymentDao();

    // Tạo bản ghi thanh toán sau khi đặt hàng thành công
    public void createPayment(long orderId, String method) {
        if (method == null || (!method.equals("COD") && !method.equals("VNPAY") && !method.equals("BANK_TRANSFER"))) {
            throw new RuntimeException("Phương thức thanh toán không hợp lệ");
        }

        Order order = new Order();
        order.setOrderId(orderId);

        Payment payment = new Payment(0, order, method, "PENDING", null);
        boolean success = paymentDao.createPayment(payment);
        if (!success) {
            throw new RuntimeException("Không thể tạo bản ghi thanh toán");
        }
    }

    // Lấy thông tin thanh toán theo Order ID
    public Payment getPaymentByOrderId(long orderId) {
        Payment payment = paymentDao.getPaymentByOrderId(orderId);
        if (payment == null) {
            throw new RuntimeException("Không tìm thấy thông tin thanh toán");
        }
        return payment;
    }

    // Cập nhật trạng thái thanh toán thành SUCCESS (dùng cho VNPAY callback / xác nhận)
    public void markPaymentSuccess(long orderId) {
        boolean success = paymentDao.updatePaymentStatus(orderId, "SUCCESS");
        if (!success) {
            throw new RuntimeException("Không thể cập nhật trạng thái thanh toán");
        }
    }

    // Cập nhật trạng thái thanh toán thành FAILED
    public void markPaymentFailed(long orderId) {
        boolean success = paymentDao.updatePaymentStatus(orderId, "FAILED");
        if (!success) {
            throw new RuntimeException("Không thể cập nhật trạng thái thanh toán");
        }
    }
}
