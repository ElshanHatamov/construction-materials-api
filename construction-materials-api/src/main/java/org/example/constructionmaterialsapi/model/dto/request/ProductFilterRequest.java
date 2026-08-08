package org.example.constructionmaterialsapi.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilterRequest {

    String name;
    Long categoryId;
    BigDecimal minPrice;
    BigDecimal maxPrice;

}
