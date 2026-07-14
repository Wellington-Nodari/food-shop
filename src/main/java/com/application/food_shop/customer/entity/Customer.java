package com.application.food_shop.customer.entity;

import com.application.food_shop.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="customers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long editorUserId;

}
