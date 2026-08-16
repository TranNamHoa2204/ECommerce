package entity;
import jakarta.persistence.*;

@Entity
@Table(name="ProductImage")
public class ProductImage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="image_id")
	private long imageId;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@Column(name="color", nullable=false, length=30)
	private String color;
	
	@Column(name="image_url", nullable=false, length=255)
	private String imageUrl;
	
	@Column(name="display_order", nullable=false)
	private int displayOrder;
	
	@Column(name="is_main", nullable=false)
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

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	
	public ProductImage(long imageId, Product product, String color, String imageUrl, int displayOrder,
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
