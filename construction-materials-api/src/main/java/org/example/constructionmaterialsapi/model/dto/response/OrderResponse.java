package org.example.constructionmaterialsapi.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    Long id;
    BigDecimal totalPrice;
    OrderStatus status;
    LocalDateTime orderDate;
    List<OrderItemResponse> items;
}
