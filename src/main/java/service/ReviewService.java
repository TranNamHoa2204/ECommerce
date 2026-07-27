package service;

import java.util.List;

import dao.ReviewDao;
import entity.Product;
import entity.Review;
import entity.User;



public class ReviewService {
    private ReviewDao reviewDao = new ReviewDao();

    // Lấy tất cả đánh giá của một sản phẩm
    public List<Review> getReviewsByProductId(long productId) {
        return reviewDao.getReviewsByProductId(productId);
    }

    // Tính điểm đánh giá trung bình của sản phẩm
    public double getAverageRating(long productId) {
        return reviewDao.getAverageRating(productId);
    }

    // Thêm đánh giá mới (có kiểm tra đã mua hàng và chưa đánh giá)
    public void addReview(long userId, long productId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Điểm đánh giá phải từ 1 đến 5");
        }
        if (comment == null || comment.isBlank()) {
            throw new RuntimeException("Nội dung đánh giá không được để trống");
        }
        if (!reviewDao.hasUserPurchasedProduct(userId, productId)) {
            throw new RuntimeException("Bạn cần mua sản phẩm này trước khi đánh giá");
        }
        if (reviewDao.hasUserReviewed(userId, productId)) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi");
        }

        User user = new User();
        user.setUserId(userId);

        Product product = new Product();
        product.setProductId(productId);

        Review review = new Review(0, user, product, rating, comment, null);
        boolean success = reviewDao.insertReview(review);
        if (!success) {
            throw new RuntimeException("Không thể lưu đánh giá, vui lòng thử lại");
        }
    }
}
