package entity;

import java.time.LocalDateTime;

public class Product {
	private long productId;
	private Category category;
	private Brand brand;
	private String name;
	private String description;
	private boolean status;
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
