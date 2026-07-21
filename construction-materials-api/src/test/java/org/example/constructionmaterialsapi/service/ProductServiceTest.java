package org.example.constructionmaterialsapi.service;

import org.example.constructionmaterialsapi.exception.AccessDeniedException;
import org.example.constructionmaterialsapi.exception.CategoryNotFoundException;
import org.example.constructionmaterialsapi.exception.ProductNotFoundException;
import org.example.constructionmaterialsapi.exception.UserNotFoundException;
import org.example.constructionmaterialsapi.mapper.ProductMapper;
import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.model.dto.response.ProductResponse;
import org.example.constructionmaterialsapi.model.entity.Category;
import org.example.constructionmaterialsapi.model.entity.Product;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.CategoryRepository;
import org.example.constructionmaterialsapi.repository.ProductRepository;
import org.example.constructionmaterialsapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductService productService;

    User user;
    Category category;
    Product product;
    ProductRequest request;
    ProductResponse response;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("elsentest@gmail.com");

        category = new Category();
        category.setId(1L);

        product = new Product();
        product.setId(10L);
        product.setSeller(user);

        request = new ProductRequest();
        request.setCategoryId(1L);

        response = new ProductResponse();
    }

    @Test
    void testCreateProduct_Success() {
        when(userRepository.findByEmail("elsentest@gmail.com")).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request, user, category)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponse res = productService.createProduct(request, "elsentest@gmail.com");

        assertNotNull(res);
        verify(productRepository).save(product);
    }

    @Test
    void testCreateProduct_UserNotFound() {
        when(userRepository.findByEmail("randomtest@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> productService.createProduct(request, "randomtest@gmail.com"));

        verify(productRepository, never()).save(any());
    }

    @Test
    void testCreateProduct_CategoryNotFound() {
        when(userRepository.findByEmail("elsentest@gmail.com")).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.createProduct(request, "elsentest@gmail.com"));
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponse res = productService.getProductById(10L);

        assertNotNull(res);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void testDeleteProductById_Success() {
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        productService.deleteProductById(10L, "elsentest@gmail.com");

        verify(productRepository).deleteById(10L);
    }

    @Test
    void testDeleteProductById_AccessDenied() {
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        assertThrows(AccessDeniedException.class,
                () -> productService.deleteProductById(10L, "wronguser@gmail.com"));

        verify(productRepository, never()).deleteById(anyLong());
    }
}