package service;

import java.util.List;

import org.springframework.stereotype.Service;

import entity.Product;
import respository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

    public List<Product> getAllProducts(){
        return productRepository.findByStatusTrue();
    }

    public Product getProductById(long id){
        Product products = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với mã: " + id));
        return products;
    }

    public List<Product> getProductsByCategoryId(long categoryId){
        List<Product> products = productRepository.findByCategoryCategoryIdAndStatusTrue(categoryId);
        return products;
    }

    public List<Product> getProductsByBrandId(long brandId){
        List<Product> products = productRepository.findByBrandBrandIdAndStatusTrue(brandId);
        return products;
    }

    public List<Product> searchProductsByName(String keyword){
        if(keyword == null || keyword.isBlank()){
            return productRepository.findByStatusTrue();
        }
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndStatusTrue(keyword);
        return products;
    }

    
}
