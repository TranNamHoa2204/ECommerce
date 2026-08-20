package controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.response.ProductResponseDTO;
import DTO.response.ProductVariantResponseDTO;
import entity.Product;
import entity.ProductVariant;
import service.ProductService;
import service.ProductVariantService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;

    public ProductController(ProductService productService, ProductVariantService productVariantService) {
        this.productService = productService;
        this.productVariantService = productVariantService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> list = productService.getAllProducts().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") long id) {
        Product product = productService.getProductById(id);
        ProductResponseDTO dto = ProductResponseDTO.fromEntity(product);

        // Lấy kèm danh sách biến thể sản phẩm
        List<ProductVariantResponseDTO> variants = productVariantService.getVariantsByProductId(id).stream()
                .map(ProductVariantResponseDTO::fromEntity)
                .toList();
        dto.setVariants(variants);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable("categoryId") long categoryId) {
        List<ProductResponseDTO> list = productService.getProductsByCategoryId(categoryId).stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByBrandId(@PathVariable("brandId") long brandId) {
        List<ProductResponseDTO> list = productService.getProductsByBrandId(brandId).stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        List<ProductResponseDTO> list = productService.searchProductsByName(keyword).stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/variants")
    public ResponseEntity<List<ProductVariantResponseDTO>> getProductVariants(@PathVariable("id") long productId) {
        List<ProductVariantResponseDTO> variants = productVariantService.getVariantsByProductId(productId).stream()
                .map(ProductVariantResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(variants);
    }
}
