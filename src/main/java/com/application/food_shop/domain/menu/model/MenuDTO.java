package com.application.food_shop.domain.menu.model;

import com.application.food_shop.domain.menu.enums.CategoryItemMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuDTO {

    private String name;
    private String description;
    private CategoryItemMenu category;
    private String image;
    private double price;
    private int quantity;

}
