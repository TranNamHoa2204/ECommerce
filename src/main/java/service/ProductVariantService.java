package service;

import java.util.List;

import dao.ProductVariantDao;
import entity.ProductVariant;



public class ProductVariantService {
    private ProductVariantDao productVariantDao = new ProductVariantDao();

    public List<ProductVariant> getVariantsByProductId(long productId) {
        return productVariantDao.getVariantsByProductId(productId);
    }

    public ProductVariant getVariantById(long variantId) {
        return productVariantDao.getVariantById(variantId);
    }
    
}
