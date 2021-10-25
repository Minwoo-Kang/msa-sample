package org.minuu.example.review;

import org.minuu.example.api.ProductApiAdapter;
import org.minuu.example.dto.ProductWithoutReviewDto;
import org.minuu.example.dto.ReviewDto;
import org.minuu.example.dto.ReviewWithProductDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ProductApiAdapter productApiAdapter;
    private final ReviewRepository reviewRepository;

    public ReviewService(ProductApiAdapter productApiAdapter,
                         ReviewRepository reviewRepository) {
        this.productApiAdapter = productApiAdapter;
        this.reviewRepository = reviewRepository;
    }


    public List<ReviewDto> findById(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        return reviews.stream()
                .map(review -> ReviewDto.builder()
                        .id(review.getId())
                        .content(review.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewWithProductDto create(Long productId, Review review) {
        ProductWithoutReviewDto product = productApiAdapter.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException());
        review.setProductId(product.getId());

        Review savedReview = reviewRepository.save(review);
        return toReviewWithProductDto(savedReview, product);
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll(Long productId) {
        reviewRepository.deleteAllByProductId(productId);
    }

    private ReviewWithProductDto toReviewWithProductDto(Review review, ProductWithoutReviewDto product) {
        return ReviewWithProductDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .product(product)
                .build();
    }

}
