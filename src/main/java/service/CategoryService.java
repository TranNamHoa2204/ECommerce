package service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Category;
import respository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
    }

    @Transactional
    public boolean insertCategory(Category category) {
        categoryRepository.save(category);
        return true;
    }   

    @Transactional
    public boolean updateCategory(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Transactional
    public boolean deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại cần xóa"));
        categoryRepository.delete(category);
        return true;
    }
}
