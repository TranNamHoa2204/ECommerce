package respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    // Lấy tất cả biến thể của một sản phẩm, sắp xếp theo giá tăng dần
    List<ProductVariant> findByProductProductIdOrderByPriceAsc(long productId);

    // Tìm biến thể theo productId + size + color (để kiểm tra trùng)
    List<ProductVariant> findByProductProductIdAndSizeAndColor(long productId, String size, String color);

    // Lấy biến thể theo SKU
    Optional<ProductVariant> findBySku(String sku);
    
    
}
