package entity;

import jakarta.persistence.*;

@Entity
@Table(name="Category")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private long categoryId;
	
	@Column(name="name", nullable=false, length=100)
	private String name;
	
	@Column(name="description", length=255)
	private String description;
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
	public Category(long categoryId, String name, String description) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
	}
	public Category() {
		
	}
	
	
}
