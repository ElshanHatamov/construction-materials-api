package org.example.constructionmaterialsapi.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.UnitType;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    Long id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    UnitType unit;

    //Satici
    Long sellerId;
    String sellerName;

    //Kategoriya
    Long categoryId;
    String categoryName;
}
