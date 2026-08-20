package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.response.CategoryResponseDTO;
import entity.Category;
import service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> list = categoryService.getAllCategories().stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryResponseDTO.fromEntity(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody Category category) {
        categoryService.insertCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponseDTO.fromEntity(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
        category.setCategoryId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.ok(CategoryResponseDTO.fromEntity(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
