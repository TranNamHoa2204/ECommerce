package DTO.response;

import java.time.LocalDateTime;

import entity.Review;

public class ReviewResponseDTO {
    private long reviewId;
    private long userId;
    private String userName;
    private long productId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewResponseDTO fromEntity(Review review) {
        if (review == null) return null;
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewId(review.getReviewId());
        if (review.getUser() != null) {
            dto.setUserId(review.getUser().getUserId());
            dto.setUserName(review.getUser().getFullName());
        }
        if (review.getProduct() != null) {
            dto.setProductId(review.getProduct().getProductId());
        }
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }

    public long getReviewId() { return reviewId; }
    public void setReviewId(long reviewId) { this.reviewId = reviewId; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
