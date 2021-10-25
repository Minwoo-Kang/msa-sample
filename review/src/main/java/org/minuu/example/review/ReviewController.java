package org.minuu.example.review;

import org.minuu.example.dto.ReviewDto;
import org.minuu.example.dto.ReviewWithProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public List<ReviewDto> findAllReviews(@PathVariable("productId") Long productId) {
        return reviewService.findById(productId);
    }

    @PostMapping("")
    public ReviewWithProductDto createReview(@PathVariable("productId") Long productId,
                                             @RequestBody Review review) {
        return reviewService.create(productId, review);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("productId") Long productId,
                             @PathVariable("id") Long id) {
        reviewService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("")
    public void deleteAllReview(@PathVariable("productId") Long productId) {
        reviewService.deleteAll(productId);
    }
}
