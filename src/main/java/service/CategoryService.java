package service;

import java.util.List;

import dao.CategoryDao;
import entity.Category;


public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    public List<Category> getAllCategories(){
        return categoryDao.getAllCategories();
    }

    public Category getCategoryById(long id){
        Category category = categoryDao.getCategoryById(id);
        if(category == null){
            throw new RuntimeException("Không tìm thấy thể loại");
        }
        return category;
    }

    public boolean insertCategory(Category category) {
        return categoryDao.insertCategory(category);
    }   
    
    public boolean updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }
    public boolean deleteCategory(long id) {
        Category category = categoryDao.getCategoryById(id);
        if(category == null){
            throw new RuntimeException("Không tìm thấy thể loại cần xóa");
        }
        return categoryDao.deleteCategory(id);
    }
}
