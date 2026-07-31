package service;

import java.util.List;

import dao.BrandDao;
import entity.Brand;



public class BrandService {
    private final BrandDao brandDao = new BrandDao();

    public List<Brand> getAllBrands(){
        List<Brand> brands = brandDao.getAllBrands();
        return brands;
    }

    public Brand getBrandById(long id){
        Brand brand = brandDao.getBrandById(id);
        if(brand == null){
            throw new RuntimeException("Không tìm thấy thương hiệu");
        }
        return brand;
    }

    
}
