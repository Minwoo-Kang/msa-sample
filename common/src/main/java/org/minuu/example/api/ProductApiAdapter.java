package org.minuu.example.api;

import org.minuu.example.dto.ProductWithoutReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "product", fallback = ProductApiAdapter.Fallback.class)
public interface ProductApiAdapter {

    @GetMapping("/products/{id}")
    Optional<ProductWithoutReviewDto> findById(@PathVariable Long id);

    @Component
    class Fallback implements ProductApiAdapter{
        @Override
        public Optional<ProductWithoutReviewDto> findById(Long productId) {
            return Optional.empty();
        }
    }
}
