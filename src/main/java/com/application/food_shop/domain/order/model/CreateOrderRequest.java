package com.application.food_shop.domain.order.model;

import java.util.List;

public record CreateOrderRequest(
        Long customerId,
        Long createdById,
        List<OrderItemRequest> orderItems
) {}
