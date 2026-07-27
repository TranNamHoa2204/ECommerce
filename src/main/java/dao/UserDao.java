package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.ConnectDB;


public class UserDao {

    // 1. Lấy user theo email để kiểm tra đăng nhập (việc verify password do Service xử lý)
    public User checkLogin(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ? AND [status] = 1";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Kiểm tra email đã tồn tại hay chưa
    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE email = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Đăng ký tài khoản khách hàng mới
    public boolean registerUser(User user) {
        String sql = "INSERT INTO [User] (full_name, email, [password], phone, [role], [status], created_at) "
                   + "VALUES (?, ?, ?, ?, 'CUSTOMER', 1, GETDATE())";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Lấy thông tin User theo ID
    public User getUserById(long userId) {
        String sql = "SELECT * FROM [User] WHERE user_id = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. Lấy danh sách tất cả người dùng (Dành cho Admin)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] ORDER BY created_at DESC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 6. Tìm tài khoản theo email
    public User getUserEmail(String email){
        String sql = "SELECT * FROM [User] WHERE email = ? AND status = 1";
        try(Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setNString(1, email);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return mapResultSetToUser(rs);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
    }

    // Helper map ResultSet sang đối tượng User
    private User mapResultSetToUser(ResultSet rs) throws Exception {
        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;
        
        return new User(
            rs.getLong("user_id"),
            rs.getNString("full_name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("phone"),
            rs.getString("role"),
            rs.getBoolean("status"),
            createdAt
        );
    }
}
