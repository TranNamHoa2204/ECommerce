package entity;

import java.math.BigDecimal;

public class OrderDetail {
	private long orderDetailId;
	private Order order;
	private ProductVariant variant;
	private BigDecimal price;
	private int quantity;
	private BigDecimal subtotal;
	public long getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ProductVariant getVariant() {
		return variant;
	}
	public void setVariant(ProductVariant variant) {
		this.variant = variant;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public OrderDetail(long orderDetailId, Order order, ProductVariant variant, BigDecimal price, int quantity,
			BigDecimal subtotal) {
		super();
		this.orderDetailId = orderDetailId;
		this.order = order;
		this.variant = variant;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	public OrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
