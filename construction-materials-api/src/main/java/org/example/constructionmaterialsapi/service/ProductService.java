package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Mehsul tapilmadi"
                ));
        return productMapper.toResponse(product);
    }

    public void deleteProductById(Long id, String ownerEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Mehsul tapilmadi"
                ));
        if (!product.getSeller().getEmail().equals(ownerEmail)) {

            throw new AccessDeniedException("Bu emeliyyati yerine yetire bilmezsiniz");
        }
        productRepository.deleteById(id);
    }
}
