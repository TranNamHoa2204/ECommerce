package respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	// Custom query methods can be defined here if needed
	@Query("SELECT od FROM OrderDetail od WHERE od.order.orderId = :orderId")
	List<OrderDetail> findByOrderId(@Param("orderId") long orderId);

	boolean existsByOrderUserUserIdAndVariantProductProductId(long userId, long productId);
}
