package service;

import java.time.LocalDateTime;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.User;
import respository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserById(long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User bạn tìm không tồn tại"));
	}

	public List<User> getAllUsers() {
		return userRepository.findAllByOrderByCreatedAtDesc();
	}

	@Transactional
	public User dangKy(String fullname, String email, String password, String phone) {
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
		if (userRepository.existsByEmail(email)) {
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
		User user = new User();
		user.setFullName(fullname);
		user.setEmail(email);
		user.setPassword(hashedPassword);
		user.setPhone(phone);
		user.setRole("CUSTOMER");
		user.setStatus(true);
		user.setCreatedAt(LocalDateTime.now());

		return userRepository.save(user);
	}

	public User dangNhap(String email, String password) {
		if (email == null || email.isBlank()) {
			throw new RuntimeException("Email không được để rỗng");
		}
		if (password == null || password.isBlank()) {
			throw new RuntimeException("Mật khẩu không được để rỗng");
		}

		// Lấy user theo email và status active
		User user = userRepository.findByEmailAndStatusTrue(email)
				.orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không chính xác"));

		// Verify password plain-text với hash trong DB
		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new RuntimeException("Email hoặc mật khẩu không chính xác");
		}

		return user;
	}
}
