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
@Table(name="Product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="product_id")
	private long productId;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	
	@Column(name="name", nullable=false, length=200)
	private String name;
	
	@Column(name="description", columnDefinition="NVARCHAR(MAX)")
	private String description;
	
	@Column(name="status", nullable=false)
	private boolean status;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public Product() {
		
	}
	
	public Product(long productId, Category category, Brand brand, String name, String description, boolean status,
			LocalDateTime createdAt) {
		super();
		this.productId = productId;
		this.category = category;
		this.brand = brand;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	
}
