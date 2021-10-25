package org.minuu.example.api;

import org.minuu.example.dto.ReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value = "review", fallback = ReviewApiAdapter.Fallback.class)
public interface ReviewApiAdapter {

    @GetMapping("/products/{productId}/reviews")
    List<ReviewDto> getReviewsByProductId(@PathVariable Long productId);

    @DeleteMapping("/products/{productId}/reviews")
    void deleteReviewByProductId(@PathVariable Long productId);

    @Component
    class Fallback implements ReviewApiAdapter {
        @Override
        public List<ReviewDto> getReviewsByProductId(Long id) {
            return new ArrayList<>();
        }

        @Override
        public void deleteReviewByProductId(Long id) {
            return;
        }
    }
}
