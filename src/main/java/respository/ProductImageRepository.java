package respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // Lấy tất cả ảnh của sản phẩm (mọi màu), sắp xếp theo màu rồi thứ tự
    List<ProductImage> findByProductProductIdOrderByColorAscDisplayOrderAsc(long productId);

    // Lấy ảnh của một màu cụ thể (dùng khi user chọn variant)
    List<ProductImage> findByProductProductIdAndColorOrderByDisplayOrderAsc(long productId, String color);

    // Lấy ảnh đại diện (is_main = true) của từng màu — dùng hiển thị card sản phẩm
    List<ProductImage> findByProductProductIdAndIsMainTrue(long productId);

    // Lấy ảnh đại diện của một màu cụ thể
    Optional<ProductImage> findByProductProductIdAndColorAndIsMainTrue(long productId, String color);
}
