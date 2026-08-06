package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.OrderStatus;
import org.example.constructionmaterialsapi.exception.ProductNotFoundException;
import org.example.constructionmaterialsapi.exception.UserNotFoundException;
import org.example.constructionmaterialsapi.mapper.OrderMapper;
import org.example.constructionmaterialsapi.model.dto.request.OrderItemRequest;
import org.example.constructionmaterialsapi.model.dto.request.OrderRequest;
import org.example.constructionmaterialsapi.model.dto.response.OrderResponse;
import org.example.constructionmaterialsapi.model.entity.Order;
import org.example.constructionmaterialsapi.model.entity.OrderItem;
import org.example.constructionmaterialsapi.model.entity.Product;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.OrderRepository;
import org.example.constructionmaterialsapi.repository.ProductRepository;
import org.example.constructionmaterialsapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(OrderRequest request, String buyerEmail) {

        User buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new UserNotFoundException("User tapilmadi: " + buyerEmail));

        Order order = new Order();
        order.setBuyer(buyer);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Mehsul tapilmadi: " + itemRequest.getProductId()));
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException(
                        String.format("'%s' mehsulundan kifayet qeder stok yoxdur. Stokda olan: %d",
                                product.getName(), product.getStock())
                );
            }
            product.setStock(product.getStock() - itemRequest.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }
        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    public Page<OrderResponse> getMyOrders(String buyerEmail, Pageable pageable) {
        return orderRepository.findByBuyerEmail(buyerEmail, pageable)
                .map(orderMapper::toOrderResponse);
    }

    public OrderResponse getOrderById(Long orderId, String buyerEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sifaris tapilmadi ID:" + orderId));
        if (!order.getBuyer().getEmail().equals(buyerEmail)) {
            throw new IllegalArgumentException("Bu sifarise baxmaq icazeniz yoxdur");
        }
        return orderMapper.toOrderResponse(order);
    }
}
