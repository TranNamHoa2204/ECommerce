package respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	// Custom query methods can be defined here if needed
	List<Order> findByUserUserIdOrderByCreatedAtDesc(long userId);
	
	@Modifying
	@Query("UPDATE Order o SET o.status = 'CANCELED' where o.orderId = :orderId AND o.status IN ('PENDING', 'PROCESSING')")
	int cancelOrder(@Param("orderId") long orderId);

}
