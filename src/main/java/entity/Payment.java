package entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Payment")
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="payment_id")
	private long paymentId;
	
	@OneToOne
	@JoinColumn(name="order_id", nullable=false, unique=true)
	private Order order;

	@Column(name="method", nullable=false, length=30)
	private String method;
	
	@Column(name="status", nullable=false, length=30)
	private String status;
	
	@Column(name="paid_at")
	private LocalDateTime paidAt;
	
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getPaidAt() {
		return paidAt;
	}
	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}
	public Payment(long paymentId, Order order, String method, String status, LocalDateTime paidAt) {
		super();
		this.paymentId = paymentId;
		this.order = order;
		this.method = method;
		this.status = status;
		this.paidAt = paidAt;
	}
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
