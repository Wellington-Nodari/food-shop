package com.application.food_shop.test;

import com.application.food_shop.domain.login.AuthController;
import com.application.food_shop.domain.login.AuthService;
import com.application.food_shop.domain.login.LoginRequest;
import com.application.food_shop.exception.GlobalExceptionHandler;
import com.application.food_shop.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() throws Exception {
        LoginRequest request =
                new LoginRequest(
                        "willnik@test.ie",
                        "12345wsaZxc!"
                );

        UserDetails user = User.builder()
                .username("willnik@test.ie")
                .password("encoded-password")
                .roles("STAFF")
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        String token = authService.login(request);

        assertEquals("jwt-token", token);
    }

    @Test
    void shouldRejectInvalidPassword() {

        LoginRequest request =
                new LoginRequest(
                        "willnik@test.ie",
                        "wrong-password"
                );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        verify(jwtService, never()).generateToken(any());
    }

}
