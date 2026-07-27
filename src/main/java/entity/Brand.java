package entity;

public class Brand {
	private long brandId;
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
