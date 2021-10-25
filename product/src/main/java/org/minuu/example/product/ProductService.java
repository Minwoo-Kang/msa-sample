package org.minuu.example.product;


import org.minuu.example.api.ReviewApiAdapter;
import org.minuu.example.dto.ProductDto;
import org.minuu.example.dto.ReviewDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ReviewApiAdapter reviewApiAdapter;

    public ProductService(ProductRepository productRepository, ReviewApiAdapter reviewApiAdapter) {
        this.productRepository = productRepository;
        this.reviewApiAdapter = reviewApiAdapter;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> {
                    List<ReviewDto> reviews = reviewApiAdapter.getReviewsByProductId(product.getId());
                    return toProductDto(product, reviews);
                })
                .collect(Collectors.toList());

        return productDtos;
    }

    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        List<ReviewDto> reviews = reviewApiAdapter.getReviewsByProductId(product.getId());

        return toProductDto(product, reviews);
    }

    @Transactional
    public ProductDto create(Product product) {
        Product savedProduct = productRepository.save(product);

        return toProductDto(savedProduct, Collections.emptyList());
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
        reviewApiAdapter.deleteReviewByProductId(id);
    }

    @Transactional
    public ProductDto update(Long id, Product newProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());

        Product savedProduct = productRepository.save(product);
        List<ReviewDto> reviews = reviewApiAdapter.getReviewsByProductId(id);

        return toProductDto(savedProduct, reviews);
    }

    private ProductDto toProductDto(Product product, List<ReviewDto> reviews) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .reviews(reviews)
                .build();
    }
}
