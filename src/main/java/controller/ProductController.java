package controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.response.ProductImageResponseDTO;
import DTO.response.ProductResponseDTO;
import DTO.response.ProductVariantResponseDTO;
import entity.Product;
import service.ProductImageService;
import service.ProductService;
import service.ProductVariantService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductVariantService productVariantService;
    private final ProductImageService productImageService;

    public ProductController(ProductService productService,
                             ProductVariantService productVariantService,
                             ProductImageService productImageService) {
        this.productService = productService;
        this.productVariantService = productVariantService;
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> list = productService.getAllProducts().stream()
                .map(this::toProductResponseWithDetails)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(toProductResponseWithDetails(product));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<ProductImageResponseDTO>> getProductImages(@PathVariable("id") long id) {
        List<ProductImageResponseDTO> images = productImageService.getImagesByProductId(id).stream()
                .map(ProductImageResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(
            @RequestParam("categoryId") long categoryId) {
        List<ProductResponseDTO> list = productService.getProductsByCategoryId(categoryId).stream()
                .map(this::toProductResponseWithDetails)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByBrandId(
            @RequestParam("brandId") long brandId) {
        List<ProductResponseDTO> list = productService.getProductsByBrandId(brandId).stream()
                .map(this::toProductResponseWithDetails)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        List<ProductResponseDTO> list = productService.searchProductsByName(keyword).stream()
                .map(this::toProductResponseWithDetails)
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

    private ProductResponseDTO toProductResponseWithDetails(Product product) {
        ProductResponseDTO dto = ProductResponseDTO.fromEntity(product);
        long productId = product.getProductId();

        List<ProductVariantResponseDTO> variants = productVariantService.getVariantsByProductId(productId).stream()
                .map(ProductVariantResponseDTO::fromEntity)
                .toList();
        dto.setVariants(variants);

        List<ProductImageResponseDTO> images = productImageService.getImagesByProductId(productId).stream()
                .map(ProductImageResponseDTO::fromEntity)
                .toList();
        dto.setImages(images);

        return dto;
    }
}
