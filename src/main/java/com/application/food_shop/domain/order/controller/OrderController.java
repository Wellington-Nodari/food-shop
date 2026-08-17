package com.application.food_shop.domain.order.controller;

import com.application.food_shop.domain.order.entity.Order;
import com.application.food_shop.domain.order.model.CreateOrderRequest;
import com.application.food_shop.domain.order.model.OrderResponse;
import com.application.food_shop.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'STAFF')")
    public ResponseEntity<OrderResponse> save(@RequestBody CreateOrderRequest orderRequest) {
        OrderResponse savedOrder = orderService.save(orderRequest);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/find/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public OrderResponse findById(@PathVariable Integer id) {
        return orderService.findById(id);
    }
}
