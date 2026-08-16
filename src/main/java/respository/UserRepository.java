package respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// 1. Lấy user đang hoạt động theo email
	Optional<User> findByEmailAndStatusTrue(String email);
	// 2. Kiểm tra email đã tồn tại
	boolean existsByEmail(String email);
	// 3. Lấy tất cả user, sắp xếp mới nhất trước
	List<User> findAllByOrderByCreatedAtDesc();
	// 4. Nếu cần tìm user theo email
	Optional<User> findByEmail(String email);
}
