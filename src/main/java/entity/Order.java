package entity;

import java.math.BigDecimal;
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
@Table(name="\"Order\"")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private long orderId;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;
	
	@Column(name="total_amount", nullable=false, precision=15, scale=2)
	private BigDecimal totalAmount;
	
	@Column(name="shipping_fee", nullable=false, precision=15, scale=2)
	private BigDecimal shippingFee;
	
	@Column(name="note", length=255)
	private String note;
	
	@Column(name="status", length=50, nullable=false)
	private String status;
	
	@Column(name="created_at")
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
