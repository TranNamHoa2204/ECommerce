package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Brand;
import entity.Category;
import entity.Product;
import util.ConnectDB;

public class ProductDao {

    // 1. Lấy tất cả sản phẩm đang hoạt động (Hiển thị Trang chủ)
    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name, c.description AS category_desc, b.name AS brand_name "
                   + "FROM [Product] p "
                   + "LEFT JOIN Category c ON p.category_id = c.category_id "
                   + "LEFT JOIN Brand b ON p.brand_id = b.brand_id "
                   + "WHERE p.status = 1 "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy sản phẩm theo ID
    public Product getProductById(long productId) {
        String sql = "SELECT p.*, c.name AS category_name, c.description AS category_desc, b.name AS brand_name "
                   + "FROM [Product] p "
                   + "LEFT JOIN Category c ON p.category_id = c.category_id "
                   + "LEFT JOIN Brand b ON p.brand_id = b.brand_id "
                   + "WHERE p.product_id = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Lấy sản phẩm theo danh mục (Category ID)
    public List<Product> getProductsByCategoryId(long categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name, c.description AS category_desc, b.name AS brand_name "
                   + "FROM [Product] p "
                   + "LEFT JOIN Category c ON p.category_id = c.category_id "
                   + "LEFT JOIN Brand b ON p.brand_id = b.brand_id "
                   + "WHERE p.category_id = ? AND p.status = 1 "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Lấy sản phẩm theo hãng (Brand ID)
    public List<Product> getProductsByBrandId(long brandId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name, c.description AS category_desc, b.name AS brand_name "
                   + "FROM [Product] p "
                   + "LEFT JOIN Category c ON p.category_id = c.category_id "
                   + "LEFT JOIN Brand b ON p.brand_id = b.brand_id "
                   + "WHERE p.brand_id = ? AND p.status = 1 "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, brandId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 5. Tìm kiếm sản phẩm theo tên
    public List<Product> searchProductsByName(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name, c.description AS category_desc, b.name AS brand_name "
                   + "FROM [Product] p "
                   + "LEFT JOIN Category c ON p.category_id = c.category_id "
                   + "LEFT JOIN Brand b ON p.brand_id = b.brand_id "
                   + "WHERE p.name LIKE ? AND p.status = 1 "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper map ResultSet thành đối tượng Product (gồm cả Category và Brand)
    private Product mapResultSetToProduct(ResultSet rs) throws Exception {
        long catId = rs.getLong("category_id");
        Category category = catId > 0 ? new Category(catId, rs.getNString("category_name"), rs.getNString("category_desc")) : null;

        long bId = rs.getLong("brand_id");
        Brand brand = bId > 0 ? new Brand(bId, rs.getNString("brand_name")) : null;

        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;

        return new Product(
            rs.getLong("product_id"),
            category,
            brand,
            rs.getNString("name"),
            rs.getNString("description"),
            rs.getBoolean("status"),
            createdAt
        );
    }
}
