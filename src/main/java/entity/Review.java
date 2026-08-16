package entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;



@Entity
@Table(name="Review")
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="review_id")
	private long reviewId;

	@ManyToOne
	@JoinColumn(name="[user_id]", nullable=false)
	private User user;

	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@Column(name="rating", nullable=false)
	private int rating;

	@Column(name="comment", nullable=false, columnDefinition="NVARCHAR(MAX)")
	private String comment;

	@Column(name="created_at")
	private LocalDateTime createdAt;

	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Review(long reviewId, User user, Product product, int rating, String comment, LocalDateTime createdAt) {
		super();
		this.reviewId = reviewId;
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
	}
	public Review() {
		super();
	}
	
	
}
