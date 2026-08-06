package org.example.constructionmaterialsapi.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

    Long productId;
    String productName;
    Integer quantity;
    BigDecimal price;
}
