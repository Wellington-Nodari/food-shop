package com.application.food_shop.domain.user.entity;

import com.application.food_shop.domain.address.entity.Address;
import com.application.food_shop.domain.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="userinfo")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).*$", message = "Password must contain a number and uppercase letter")
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotNull
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long editorUserId;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Address> addresses;

    public User(String email, String password, UserRole userRole, Boolean aTrue, LocalDateTime now) {
        this.email = email;
        this.password = password;
        this.role = userRole;
        this.active = aTrue;
        this.createdAt = now;
    }

}
