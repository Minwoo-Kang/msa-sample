package org.minuu.example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewWithProductDto {

    private Long id;
    private String content;

    private ProductWithoutReviewDto product;
}
