package com.application.food_shop.test;

import com.application.food_shop.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService("super-secret-key-temporary-for-development-only");
    }

    @Test
    @DisplayName("Should Generate a JWT token and extract username")
    void shouldGenerateJWTTokenAndExtractUsername() {
        UserDetails user = User.builder()
                .username("user@mail.com")
                .password("Password123!")
                .roles("STAFF")
                .build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals("user@mail.com", jwtService.extractUsername(token));
    }

}
