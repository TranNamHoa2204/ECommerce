package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Product;
import entity.ProductVariant;
import util.ConnectDB;

public class ProductVariantDao {

    // 1. Lấy danh sách biến thể theo ID sản phẩm
    public List<ProductVariant> getVariantsByProductId(long productId) {
        List<ProductVariant> list = new ArrayList<>();
        String sql = "SELECT v.*, p.name AS product_name "
                + "FROM ProductVariant v "
                + "INNER JOIN [Product] p ON v.product_id = p.product_id "
                + "WHERE v.product_id = ? ORDER BY v.price ASC";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(helper(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // 2. Lấy thông tin biến thể theo Variant ID
    public ProductVariant getVariantById(long variantId) {
        String sql = "SELECT v.*, p.name AS product_name "
                + "FROM ProductVariant v "
                + "INNER JOIN [Product] p ON v.product_id = p.product_id "
                + "WHERE v.variant_id = ?";
        try (Connection conn = ConnectDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getLong("product_id"));
                    p.setName(rs.getNString("product_name"));

                    return new ProductVariant(
                            rs.getLong("variant_id"),
                            p,
                            rs.getString("size"),
                            rs.getNString("color"),
                            rs.getBigDecimal("price"),
                            rs.getInt("stock"),
                            rs.getString("sku"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 3. Tìm biến thể
    public List<ProductVariant> findVariant(long productId, String size, String color) {
        List <ProductVariant> list = new ArrayList<>();
        String sql = "SELECT v.*, p.name AS product_name "
                + "FROM ProductVariant v "
                + "INNER JOIN [Product] p ON v.product_id = p.product_id "
                + "WHERE p.product_id = ? AND v.size = ? AND v.color = ?";
        
        try (Connection conn = ConnectDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, productId);
            ps.setString(2, size);
            ps.setNString(3, color); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(helper(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm biến thể sản phẩm", e);
        }
        return list; 
    }
    
    public ProductVariant helper(ResultSet rs){
        try {
            Product product = new Product();
            product.setProductId(rs.getLong("product_id"));
            product.setName(rs.getNString("product_name"));
            
            // Tạo và trả về ProductVariant
            return new ProductVariant(
                rs.getLong("variant_id"),
                product,
                rs.getString("size"),          
                rs.getNString("color"),        
                rs.getBigDecimal("price"),
                rs.getInt("stock"),
                rs.getString("sku")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm biến thể");
        }
    }
}
