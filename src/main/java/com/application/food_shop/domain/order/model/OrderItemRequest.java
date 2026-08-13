package com.application.food_shop.domain.order.model;

public record OrderItemRequest(
        Long menuId,
        Integer quantity
) { }
