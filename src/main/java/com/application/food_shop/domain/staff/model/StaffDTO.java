package com.application.food_shop.domain.staff.model;

import com.application.food_shop.domain.staff.enums.StaffPositions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private StaffPositions position;
    private String phoneNumber;
}
