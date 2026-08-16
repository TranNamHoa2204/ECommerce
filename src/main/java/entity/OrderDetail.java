package entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name="OrderDetail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_detail_id")
	private long orderDetailId;
	
	@ManyToOne
	@JoinColumn(name="order_id", nullable=false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="variant_id", nullable=false)
	private ProductVariant variant;
	
	@Column(name="price", nullable=false, precision=15, scale=2)
	private BigDecimal price;
	
	@Column(name="quantity", nullable=false)
	private int quantity;
	
	@Column(name="subtotal", nullable=false, precision=15, scale=2)
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
