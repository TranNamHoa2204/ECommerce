package respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Lấy tất cả items trong giỏ
    List<CartItem> findByCartCartId(long cartId);

    // Tìm item cụ thể theo cartId + variantId (để addOrUpdate)
    Optional<CartItem> findByCartCartIdAndVariantVariantId(long cartId, long variantId);

    // Xóa toàn bộ items trong giỏ (dùng sau khi đặt hàng)
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId")
    void deleteAllByCartId(@Param("cartId") long cartId);

    // Đếm số lượng items (dùng hiển thị badge trên icon giỏ hàng)
    int countByCartCartId(long cartId);
}
