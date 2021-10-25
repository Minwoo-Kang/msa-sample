package org.minuu.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

    private Long id;
    private String content;
}
