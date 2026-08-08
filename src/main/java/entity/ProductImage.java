package entity;

public class ProductImage {
	private long imageId;
	private Product product;
	private String color;
	private String imageUrl;
	private String displayOrder;
	private boolean isMain;

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	
	public ProductImage(long imageId, Product product, String color, String imageUrl, String displayOrder,
			boolean isMain) {
		super();
		this.imageId = imageId;
		this.product = product;
		this.color = color;
		this.imageUrl = imageUrl;
		this.displayOrder = displayOrder;
		this.isMain = isMain;
	}

	public ProductImage() {
		
	}
	
	
}
