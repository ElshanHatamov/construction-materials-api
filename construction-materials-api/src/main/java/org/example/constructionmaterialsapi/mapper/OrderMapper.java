package org.example.constructionmaterialsapi.mapper;

import org.example.constructionmaterialsapi.model.dto.response.OrderItemResponse;
import org.example.constructionmaterialsapi.model.dto.response.OrderResponse;
import org.example.constructionmaterialsapi.model.entity.Order;
import org.example.constructionmaterialsapi.model.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemResponse toItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

    public OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .items(itemResponses)
                .build();
    }
}
