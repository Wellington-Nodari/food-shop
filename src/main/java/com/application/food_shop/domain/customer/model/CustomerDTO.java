package com.application.food_shop.domain.customer.model;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String phoneNumber;

}
