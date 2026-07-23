package org.example.constructionmaterialsapi.repository;

import org.example.constructionmaterialsapi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> getByCategoryId(Long categoryId, Pageable pageable);
}
