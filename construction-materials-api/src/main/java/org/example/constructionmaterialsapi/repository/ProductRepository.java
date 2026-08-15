package org.example.constructionmaterialsapi.repository;

import lombok.NonNull;
import org.example.constructionmaterialsapi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @EntityGraph(attributePaths = {"categories", "seller"})
    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "seller"})
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findProductById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"categories", "seller"})
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    Page<Product> getByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "seller"})
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "seller"})
    Page<Product> findByPriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "seller"})
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId AND p.price BETWEEN :minPrice AND :maxPrice AND p.active = true")
    Page<Product> findByCategoryIdAndPriceBetweenAndActiveTrue(@Param("categoryId") Long categoryId,
                                                               @Param("minPrice") BigDecimal minPrice,
                                                               @Param("maxPrice") BigDecimal maxPrice,
                                                               Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "seller"})
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.categories c " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:categoryId IS NULL OR c.id = :categoryId) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(p.active = true)")
    Page<Product> filterProducts(@Param("name") String name,
                                 @Param("categoryId") Long categoryId,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice,
                                 Pageable pageable);
}
