package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.exception.AccessDeniedException;
import org.example.constructionmaterialsapi.exception.CategoryNotFoundException;
import org.example.constructionmaterialsapi.exception.ProductNotFoundException;
import org.example.constructionmaterialsapi.exception.UserNotFoundException;
import org.example.constructionmaterialsapi.mapper.ProductMapper;
import org.example.constructionmaterialsapi.model.dto.request.ProductFilterRequest;
import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.model.dto.response.ProductResponse;
import org.example.constructionmaterialsapi.model.entity.Category;
import org.example.constructionmaterialsapi.model.entity.Product;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.CategoryRepository;
import org.example.constructionmaterialsapi.repository.ProductRepository;
import org.example.constructionmaterialsapi.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    UserRepository userRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        "IStifadeci tapilmadi")
                );
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Kategoriya tapilmadi"
                ));
        Product product = productMapper.toEntity(request, owner, category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Mehsul tapilmadi"
                ));
        return productMapper.toResponse(product);
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProductById(Long id, String ownerEmail) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Mehsul tapilmadi"
                ));
        if (!product.getSeller().getEmail().equals(ownerEmail)) {

            throw new AccessDeniedException("Bu emeliyyati yerine yetire bilmezsiniz");
        }
        productRepository.deleteById(id);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable)
                .map(productMapper::toResponse);
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse updateProduct(Long id, ProductRequest request, String ownerEmail) {

        Product product = productRepository.findProductById(id).
                orElseThrow(() -> new ProductNotFoundException(
                        "Mehsul Tapilmadi"));
        if (!product.getSeller().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("Bu Emeliyyati yerine yetire bilmezsiniz");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Kateegoriya tapilmadi"));

        product.getCategories().clear();
        product.getCategories().add(category);
        productMapper.updateEntityFromDto(product, request, product.getSeller(), category);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);

    }

    public Page<ProductResponse> filterProducts(ProductFilterRequest request,
                                                Pageable pageable) {
        if (request.getMinPrice() != null && request.getMaxPrice() != null
                && request.getMinPrice().compareTo(request.getMaxPrice()) > 0) {
            throw new IllegalArgumentException("Minimum qiymet maximum qiymetden boyuk ola bilmez");
        }
        Page<Product> products = productRepository.filterProducts(request.getName(),
                request.getCategoryId(),
                request.getMinPrice(),
                request.getMaxPrice(),
                pageable);

        return products.map(productMapper::toResponse);
    }
}
