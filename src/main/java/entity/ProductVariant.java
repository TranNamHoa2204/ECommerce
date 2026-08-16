package entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ProductVariant")
public class ProductVariant {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="variant_id")
	private long variantId;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@Column(name="size", length=10)
	private String size;
	
	@Column(name="color", length=30)
	private String color;
	
	@Column(name="price", nullable=false, precision=15, scale=2)
	private BigDecimal price;
	
	@Column(name="stock", nullable=false)
	private int stock;
	
	@Column(name="sku", length=50, unique=true)
	private String sku;
	
	public long getVariantId() {
		return variantId;
	}
	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public ProductVariant(long variantId, Product product, String size, String color, BigDecimal price, int stock,
			String sku) {
		super();
		this.variantId = variantId;
		this.product = product;
		this.size = size;
		this.color = color;
		this.price = price;
		this.stock = stock;
		this.sku = sku;
	}
	public ProductVariant() {
		
	}
	
	
	
}
