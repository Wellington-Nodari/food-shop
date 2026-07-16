package com.application.food_shop.domain.address.entity;

import com.application.food_shop.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String postalCode;

    public Address(User user, String street, String number, String complement, String city, String postalCode) {
        this.user = user;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.postalCode = postalCode;
    }
}
