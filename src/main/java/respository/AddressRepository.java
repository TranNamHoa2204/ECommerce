package respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Lấy tất cả địa chỉ của một user
    List<Address> findByUserUserId(long userId);

    // Lấy địa chỉ mặc định của user
    Optional<Address> findByUserUserIdAndIsDefaultTrue(long userId);

    // Kiểm tra địa chỉ có thuộc về user không (dùng để xác thực quyền)
    boolean existsByAddressIdAndUserUserId(long addressId, long userId);

    // Bỏ tất cả địa chỉ default của user trước khi set default mới
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.userId = :userId")
    void clearDefaultByUserId(@Param("userId") long userId);
}
