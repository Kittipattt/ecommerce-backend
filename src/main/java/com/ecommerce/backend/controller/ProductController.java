package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiResponse;
import com.ecommerce.backend.dto.ProductRequest;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Product>>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Page<Product> products = productService.getProducts(categoryId, search, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(ApiResponse.ok("Products retrieved", products));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<Product>>> getFeaturedProducts() {
        List<Product> featured = productService.getFeaturedProducts();
        return ResponseEntity.ok(ApiResponse.ok("Featured products", featured));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductRequest request) {
        Product created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Product created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Product updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.ok("Product deleted successfully", null));
    }
}
