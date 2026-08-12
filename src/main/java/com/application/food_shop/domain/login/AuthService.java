package com.application.food_shop.domain.login;

import com.application.food_shop.security.CustomUserDetailsService;
import com.application.food_shop.security.JwtService;
import com.application.food_shop.security.TokenBlacklistService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public String login(LoginRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        if (user == null) {
            throw new BadCredentialsException("Username or password is invalid");
        }
        return jwtService.generateToken(user);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7).trim();

            try {
                Date expiration = jwtService.getExpirationDate(jwt);

                long remainingTime =
                        expiration.getTime() - System.currentTimeMillis();

                if (remainingTime > 0) {
                    tokenBlacklistService.addToBlacklist(jwt, remainingTime);
                }
            } catch (JwtException e) {
                log.debug("Invalid JWT supplied during logout");
            }
        }

        SecurityContextHolder.clearContext();
    }
}
