package entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Cart")
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private long cartId;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, unique=true)
	private User user;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Cart(long cartId, User user, LocalDateTime createdAt) {
		super();
		this.cartId = cartId;
		this.user = user;
		this.createdAt = createdAt;
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
