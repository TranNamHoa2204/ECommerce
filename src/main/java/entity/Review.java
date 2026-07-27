package entity;

import java.time.LocalDateTime;

public class Review {
	private long reviewId;
	private User user;
	private Product product;
	private int rating;
	private String comment;
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
		// TODO Auto-generated constructor stub
	}
	
	
}
