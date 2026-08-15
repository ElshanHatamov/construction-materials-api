package org.example.constructionmaterialsapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.UnitType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    BigDecimal price;
    @NotNull
    Integer stock;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UnitType unit;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    User seller;

    @OneToMany(mappedBy = "product")
    List<OrderItem> orderItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    Set<Category> categories = new HashSet<>();
}
