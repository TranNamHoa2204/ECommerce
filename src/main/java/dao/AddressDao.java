package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Address;
import entity.User;
import util.ConnectDB;

public class AddressDao {

    // 1. Lấy tất cả địa chỉ của một người dùng
    public List<Address> getAddressesByUserId(long userId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM [Address] WHERE user_id = ? ORDER BY is_default DESC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAddress(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy địa chỉ theo ID
    public Address getAddressById(long addressId) {
        String sql = "SELECT * FROM [Address] WHERE address_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAddress(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Lấy địa chỉ mặc định của người dùng
    public Address getDefaultAddress(long userId) {
        String sql = "SELECT * FROM [Address] WHERE user_id = ? AND is_default = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAddress(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Thêm địa chỉ mới
    public boolean insertAddress(Address address) {
        String sql = "INSERT INTO [Address] (user_id, receiver_name, phone, province, district, ward, detail_address, is_default) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, address.getUser().getUserId());
            ps.setNString(2, address.getReceiverName());
            ps.setString(3, address.getPhone());
            ps.setNString(4, address.getProvince());
            ps.setNString(5, address.getDistrict());
            ps.setNString(6, address.getWard());
            ps.setNString(7, address.getDetail_address());
            ps.setBoolean(8, address.isDefault());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Cập nhật địa chỉ
    public boolean updateAddress(Address address) {
        String sql = "UPDATE [Address] SET receiver_name = ?, phone = ?, province = ?, district = ?, "
                   + "ward = ?, detail_address = ?, is_default = ? WHERE address_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, address.getReceiverName());
            ps.setString(2, address.getPhone());
            ps.setNString(3, address.getProvince());
            ps.setNString(4, address.getDistrict());
            ps.setNString(5, address.getWard());
            ps.setNString(6, address.getDetail_address());
            ps.setBoolean(7, address.isDefault());
            ps.setLong(8, address.getAddressId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Xóa địa chỉ
    public boolean deleteAddress(long addressId) {
        String sql = "DELETE FROM [Address] WHERE address_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, addressId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Đặt địa chỉ làm mặc định (bỏ default cũ, set default mới)
    public boolean setDefaultAddress(long userId, long addressId) {
        String clearSql = "UPDATE [Address] SET is_default = 0 WHERE user_id = ?";
        String setSql   = "UPDATE [Address] SET is_default = 1 WHERE address_id = ? AND user_id = ?";
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psClear = conn.prepareStatement(clearSql)) {
                psClear.setLong(1, userId);
                psClear.executeUpdate();
            }

            try (PreparedStatement psSet = conn.prepareStatement(setSql)) {
                psSet.setLong(1, addressId);
                psSet.setLong(2, userId);
                int rows = psSet.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        }
        return false;
    }

    // Helper map ResultSet sang đối tượng Address
    private Address mapResultSetToAddress(ResultSet rs) throws Exception {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));

        return new Address(
            rs.getLong("address_id"),
            user,
            rs.getNString("receiver_name"),
            rs.getString("phone"),
            rs.getNString("province"),
            rs.getNString("district"),
            rs.getNString("ward"),
            rs.getNString("detail_address"),
            rs.getBoolean("is_default")
        );
    }
}
