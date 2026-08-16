package entity;
import jakarta.persistence.*;

@Entity
@Table(name="Brand")
public class Brand {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="brand_id")
	private long brandId;
	
	@Column(name="name", nullable=false, length=255)
	private String name;
	
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Brand(long brandId, String name) {
		super();
		this.brandId = brandId;
		this.name = name;
	}
	public Brand() {
		
	}
	
}
