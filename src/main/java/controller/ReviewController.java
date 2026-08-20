package controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.request.ReviewRequestDTO;
import DTO.response.ReviewResponseDTO;
import entity.Review;
import jakarta.validation.Valid;
import service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProductId(@PathVariable("productId") long productId) {
        List<ReviewResponseDTO> list = reviewService.getReviewsByProductId(productId).stream()
                .map(ReviewResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/product/{productId}/rating")
    public ResponseEntity<Map<String, Double>> getAverageRating(@PathVariable("productId") long productId) {
        double averageRating = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(Map.of("averageRating", averageRating));
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ReviewResponseDTO> addReview(
            @PathVariable("userId") long userId,
            @PathVariable("productId") long productId,
            @Valid @RequestBody ReviewRequestDTO request) {

        Review review = reviewService.addReview(userId, productId, request.getRating(), request.getComment());
        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponseDTO.fromEntity(review));
    }
}
