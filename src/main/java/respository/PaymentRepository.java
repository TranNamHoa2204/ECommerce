package respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Lấy payment theo orderId (quan hệ 1-1)
    Optional<Payment> findByOrderOrderId(long orderId);
}
