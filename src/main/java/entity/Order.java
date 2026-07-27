package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
	private long orderId;
	private User user;
	private Address address;
	private BigDecimal totalAmount;
	private BigDecimal shippingFee;
	private String note;
	private String status;
	private LocalDateTime createdAt;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Order(long orderId, User user, Address address, BigDecimal totalAmount, BigDecimal shippingFee, String note,
			String status, LocalDateTime createdAt) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.address = address;
		this.totalAmount = totalAmount;
		this.shippingFee = shippingFee;
		this.note = note;
		this.status = status;
		this.createdAt = createdAt;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
