package org.example.constructionmaterialsapi.service;

import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductServiceRollbackTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct_WhenExceptionOccurs_ShouldRollbackTransaction() {
        long initialCount = productRepository.count();

        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setCategoryId(99999L);

        assertThrows(Exception.class, () -> {
            productService.createProduct(request, "test@gmail.com");
        });

        long finalCount = productRepository.count();

        assertEquals(initialCount, finalCount);
    }
}