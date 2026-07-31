package service;

import java.util.List;

import dao.ProductDao;
import entity.Product;


public class ProductService {
    private final ProductDao  productDao = new ProductDao();

    public List<Product> getAllProducts(){
        return productDao.getAllActiveProducts();
    }

    public Product getProductById(long id){
        Product products = productDao.getProductById(id);
        if(products == null){
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        return products;
    }

    public List<Product> getProductsByCategoryId(long categoryId){
        List<Product> products = productDao.getProductsByCategoryId(categoryId);
        return products;
    }

    public List<Product> getProductsByBrandId(long brandId){
        List<Product> products = productDao.getProductsByBrandId(brandId);
        return products;
    }

    public List<Product> searchProductsByName(String keyword){
        if(keyword == null || keyword.isBlank()){
            return productDao.getAllActiveProducts();
        }
        List<Product> products = productDao.searchProductsByName(keyword);
        return products;
    }

    
}
