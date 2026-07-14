package com.application.food_shop.user.entity;

import com.application.food_shop.address.entity.Address;
import com.application.food_shop.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long editorUserId;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Address> addresses;

}
