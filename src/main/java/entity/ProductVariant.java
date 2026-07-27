package entity;

import java.math.BigDecimal;

public class ProductVariant {
	private long variantId;
	private Product product;
	private String size;
	private String color;
	private BigDecimal price;
	private int stock;
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
