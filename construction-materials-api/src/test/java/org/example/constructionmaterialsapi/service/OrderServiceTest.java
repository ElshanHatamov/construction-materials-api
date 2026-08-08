package org.example.constructionmaterialsapi.service;

import org.example.constructionmaterialsapi.model.dto.request.OrderRequest;
import org.example.constructionmaterialsapi.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void givenInvalidOrder_whenCreateOrder_thenRollbackTransaction() {
        OrderRequest request = new OrderRequest();
        request.setItems(new ArrayList<>());

        assertThrows(Exception.class, () -> {
            orderService.createOrder(request, "elsentest@gmail.com");
        });

        assertEquals(0, orderRepository.count());
    }
}