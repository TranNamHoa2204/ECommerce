package entity;

import java.time.LocalDateTime;

public class Cart {
	private long cartId;
	private User user;
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
