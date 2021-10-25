package org.minuu.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private Integer price;
    List<ReviewDto> reviews;

}

