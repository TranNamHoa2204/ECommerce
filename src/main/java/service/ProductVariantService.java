package service;

import java.util.List;

import org.springframework.stereotype.Service;

import entity.ProductVariant;
import respository.ProductVariantRepository;

@Service
public class ProductVariantService {
	private final ProductVariantRepository productVariantRepository;
	
	public ProductVariantService(ProductVariantRepository productVariantRepository) {
		this.productVariantRepository = productVariantRepository;
	}
	
    public List<ProductVariant> getVariantsByProductId(long productId) {
        return productVariantRepository.findByProductProductIdOrderByPriceAsc(productId);
    }

    public ProductVariant getVariantById(long variantId) {
        return productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy biến thể với mã: " + variantId));
    }
    
    public List<ProductVariant> findVariant(long productId, String size, String color) {
        return productVariantRepository.findByProductProductIdAndSizeAndColor(productId, size, color);
    }
}
