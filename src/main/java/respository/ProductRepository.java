package respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// Custom query methods can be defined here if needed
	List<Product> findByStatusTrue();
	List<Product> findByCategoryCategoryIdAndStatusTrue(long categoryId);
	List<Product> findByBrandBrandIdAndStatusTrue(long brandId);
	List<Product> findByNameContainingIgnoreCaseAndStatusTrue(String keyword);
	
}
