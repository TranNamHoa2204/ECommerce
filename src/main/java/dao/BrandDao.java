package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Brand;
import util.ConnectDB;

public class BrandDao {

    public List<Brand> getAllBrands() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT * FROM Brand ORDER BY name ASC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Brand(
                    rs.getLong("brand_id"),
                    rs.getNString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Brand getBrandById(long brandId) {
        String sql = "SELECT * FROM Brand WHERE brand_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, brandId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Brand(
                        rs.getLong("brand_id"),
                        rs.getNString("name")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
