package entity;

public class ProductImage {
	private long imageId;
	private Product product;
	private String imageUrl;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isMain() {
		return isMain;
	}
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	public ProductImage(long imageId, Product product, String imageUrl, boolean isMain) {
		super();
		this.imageId = imageId;
		this.product = product;
		this.imageUrl = imageUrl;
		this.isMain = isMain;
	}
	public ProductImage() {
		
	}
	
	
}
