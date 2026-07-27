package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import util.ConnectDB;

public class CategoryDao {

    // 1. Lấy tất cả thể loại sản phẩm
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY name ASC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(
                    rs.getLong("category_id"),
                    rs.getNString("name"),
                    rs.getNString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy danh mục theo ID
    public Category getCategoryById(long categoryId) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                        rs.getLong("category_id"),
                        rs.getNString("name"),
                        rs.getNString("description")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Thêm mới thể loại (Admin)
    public boolean insertCategory(Category category) {
        String sql = "INSERT INTO Category (name, description) VALUES (?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, category.getName());
            ps.setNString(2, category.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật thể loại (Admin)
    public boolean updateCategory(Category category) {
        String sql = "UPDATE Category SET name = ?, description = ? WHERE category_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, category.getName());
            ps.setNString(2, category.getDescription());
            ps.setLong(3, category.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa thể loại (Admin)
    public boolean deleteCategory(long categoryId) {
        String sql = "DELETE FROM Category WHERE category_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
