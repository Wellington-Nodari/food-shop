package com.application.food_shop.domain.order.model;

public record OrderItemsResponse(
        String name,
        Double price,
        Integer quantity
) {
}
