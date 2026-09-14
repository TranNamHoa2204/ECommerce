package service;

import java.util.List;

import org.springframework.stereotype.Service;

import entity.ProductImage;
import respository.ProductImageRepository;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    // Lấy tất cả ảnh của sản phẩm, sắp xếp theo màu rồi thứ tự
    public List<ProductImage> getImagesByProductId(long productId) {
        return productImageRepository
                .findByProductProductIdOrderByColorAscDisplayOrderAsc(productId);
    }

    // Lấy ảnh của một màu cụ thể
    public List<ProductImage> getImagesByProductIdAndColor(long productId, String color) {
        return productImageRepository
                .findByProductProductIdAndColorOrderByDisplayOrderAsc(productId, color);
    }
}
