package org.example.constructionmaterialsapi.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.model.dto.response.ProductResponse;
import org.example.constructionmaterialsapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request,
                                                         @RequestParam String ownerEmail) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(
                        request, ownerEmail
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id,
                                                 @RequestParam String ownerEmail) {
        productService.deleteProductById(id, ownerEmail);
        return ResponseEntity.noContent().build();
    }

}
