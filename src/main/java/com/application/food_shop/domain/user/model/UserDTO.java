package com.application.food_shop.domain.user.model;

import com.application.food_shop.domain.user.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).*$", message = "Password must contain a number and uppercase letter")
    private String password;
    private UserRole userRole;
    private Boolean isActive;

}
