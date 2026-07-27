package service;

import java.util.List;

import dao.ProductDao;
import entity.Product;


public class ProductService {
    ProductDao productDao = new ProductDao();

    public List<Product> getAllProducts(){
        return productDao.getAllActiveProducts();
    }

    public Product getProductById(long id){
        Product product = productDao.getProductById(id);
        if(product == null){
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        return product;
    }

    public List<Product> getProductsByCategoryId(long categoryId){
        List<Product> products = productDao.getProductsByCategoryId(categoryId);
        return products;
    }

    public List<Product> searchProductsByName(String keyword){
        List<Product> products = productDao.searchProductsByName(keyword);
        return products;
    }
}
