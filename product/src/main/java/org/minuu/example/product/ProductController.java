package org.minuu.example.product;

import org.minuu.example.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<ProductDto> productList() {
        List<ProductDto> products = productService.findAll();
        return products;
    }

    @PostMapping("")
    public ProductDto productCreate(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    public ProductDto productDetail(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ProductDto productUpdate(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void productDelete(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }

}
