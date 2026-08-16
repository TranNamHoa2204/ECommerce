package respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Lấy tất cả review của một sản phẩm, mới nhất trước
    List<Review> findByProductProductIdOrderByCreatedAtDesc(long productId);

    // Lấy tất cả review của một user
    List<Review> findByUserUserIdOrderByCreatedAtDesc(long userId);

    // Kiểm tra user đã review sản phẩm này chưa
    boolean existsByUserUserIdAndProductProductId(long userId, long productId);

    // Tính điểm trung bình rating của một sản phẩm
    @Query("SELECT COALESCE(AVG(CAST(r.rating AS double)), 0.0) FROM Review r WHERE r.product.productId = :productId")
    double getAverageRatingByProductId(@Param("productId") long productId);
}
