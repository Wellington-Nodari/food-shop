package com.application.food_shop.domain.order.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        LocalDateTime createdAt,
        Long createdById,
        String createdByName,
        Long customerId,
        String customerName,
        String customerPhone,
        List<OrderItemsResponse> orderItems,
        BigDecimal totalPrice
) {
}
