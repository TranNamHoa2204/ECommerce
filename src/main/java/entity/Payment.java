package entity;

import java.time.LocalDateTime;

public class Payment {
	private long paymentId;
	private Order order;
	private String method;
	private String status;
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
