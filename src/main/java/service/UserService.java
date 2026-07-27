package service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDao;
import entity.User;


public class UserService {
	private UserDao userDao = new UserDao();

	public User getUserById(long userId) {
		User user = userDao.getUserById(userId);
		if (user == null) {
			throw new RuntimeException("User bạn tìm không tồn tại");
		}
		return user;
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public void dangKy(String fullname, String email, String password, String phone) {
		if (fullname == null || fullname.isBlank()) {
			throw new RuntimeException("Tên không được để rỗng");
		}

		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (email == null || email.isBlank()) {
			throw new RuntimeException("Email không được để rỗng");
		}
		if (!email.matches(emailRegex)) {
			throw new RuntimeException("Email không hợp lệ");
		}
		if (userDao.checkEmailExists(email)) {
			throw new RuntimeException("Email đã được sử dụng");
		}
		if (password == null || password.isBlank()) {
			throw new RuntimeException("Mật khẩu không được để trống");
		}
		if (phone == null || phone.isBlank()) {
			throw new RuntimeException("Số điện thoại không được để trống");
		}
		if (!phone.matches("^0\\d{9}$")) {
			throw new RuntimeException("Số điện thoại không hợp lệ");
		}

		// Hash password trước khi lưu vào DB
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		User user = new User(fullname, email, hashedPassword, phone);
		userDao.registerUser(user);
	}

	public User dangNhap(String email, String password) {
		if (email == null || email.isBlank()) {
			throw new RuntimeException("Email không được để rỗng");
		}
		if (password == null || password.isBlank()) {
			throw new RuntimeException("Mật khẩu không được để rỗng");
		}

		// Lấy user theo email, không đưa password vào SQL
		User user = userDao.checkLogin(email);
		if (user == null) {
			throw new RuntimeException("Email hoặc mật khẩu không chính xác");
		}

		// Verify password plain-text với hash trong DB
		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new RuntimeException("Email hoặc mật khẩu không chính xác");
		}

		return user;
	}
}
