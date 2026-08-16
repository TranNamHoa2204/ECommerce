package respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Lấy giỏ hàng theo userId (mỗi user chỉ có 1 giỏ)
    Optional<Cart> findByUserUserId(long userId);
}
