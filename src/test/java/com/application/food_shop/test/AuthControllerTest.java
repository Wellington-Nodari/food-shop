package com.application.food_shop.test;

import com.application.food_shop.domain.login.AuthController;
import com.application.food_shop.domain.login.AuthService;
import com.application.food_shop.domain.login.LoginRequest;
import com.application.food_shop.exception.GlobalExceptionHandler;
import com.application.food_shop.security.CustomUserDetailsService;
import com.application.food_shop.security.JwtAuthenticationFilter;
import com.application.food_shop.security.JwtService;
import com.application.food_shop.security.TokenBlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false) // Isolates the Controller and ignores any blockers from JWT/Blacklist
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnTokenOnValidLogin() throws Exception {
        LoginRequest request = new LoginRequest("willnik@test.ie", "12345wsaZxc!");

        when(authService.login(any(LoginRequest.class)))
                .thenReturn("mocked-jwt-token-xyz");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token-xyz"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        LoginRequest request = new LoginRequest("willnik@test.ie", "anyPassword");

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void shouldLogoutSuccessfully() throws Exception {
        Mockito.doNothing().when(authService).logout(any(), any());

        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isNoContent());

        Mockito.verify(authService, Mockito.times(1)).logout(any(), any());
    }
}
