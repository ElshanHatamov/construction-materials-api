package org.example.constructionmaterialsapi.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.model.dto.request.OrderRequest;
import org.example.constructionmaterialsapi.model.dto.response.OrderResponse;
import org.example.constructionmaterialsapi.security.CustomUserDetails;
import org.example.constructionmaterialsapi.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders" )
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request, userDetails.getUsername()));
    }

    @GetMapping("/my" )
    public ResponseEntity<Page<OrderResponse>> getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           Pageable pageable) {
        return ResponseEntity.ok(orderService.getMyOrders(userDetails.getUsername(), pageable));
    }

    @GetMapping("/{id}" )
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getOrderById(id, userDetails.getUsername()));
    }
}
