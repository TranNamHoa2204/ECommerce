package service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Product;
import entity.Review;
import entity.User;
import respository.OrderDetailRepository;
import respository.ProductRepository;
import respository.ReviewRepository;
import respository.UserRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         OrderDetailRepository orderDetailRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Lấy tất cả đánh giá của một sản phẩm
    public List<Review> getReviewsByProductId(long productId) {
        return reviewRepository.findByProductProductIdOrderByCreatedAtDesc(productId);
    }

    // Tính điểm đánh giá trung bình của sản phẩm
    public double getAverageRating(long productId) {
        return reviewRepository.getAverageRatingByProductId(productId);
    }

    // Thêm đánh giá mới (có kiểm tra đã mua hàng và chưa đánh giá)
    @Transactional
    public Review addReview(long userId, long productId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Điểm đánh giá phải từ 1 đến 5");
        }
        if (comment == null || comment.isBlank()) {
            throw new RuntimeException("Nội dung đánh giá không được để trống");
        }
        if (!orderDetailRepository.existsByOrderUserUserIdAndVariantProductProductId(userId, productId)) {
            throw new RuntimeException("Bạn cần mua sản phẩm này trước khi đánh giá");
        }
        if (reviewRepository.existsByUserUserIdAndProductProductId(userId, productId)) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }
}
