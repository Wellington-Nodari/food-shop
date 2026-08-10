package com.application.food_shop.domain.user.service;

import com.application.food_shop.domain.user.entity.User;
import com.application.food_shop.domain.user.model.UserDTO;
import com.application.food_shop.domain.user.repository.UserRepository;
import com.application.food_shop.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO findUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        return new UserDTO(user.getEmail(), null, user.getRole(), user.getActive());
    }

    @Transactional
    public void updateUser(Long id, UserDTO dto){
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        if(dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())){
            user.setEmail(dto.getEmail());
        }
        if(dto.getPassword() != null && !passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if(dto.getUserRole() != null && !dto.getUserRole().equals(user.getRole())){
            user.setRole(dto.getUserRole());
        }
        if(dto.getIsActive() != null && !dto.getIsActive().equals(user.getActive())) {
            user.setActive(dto.getIsActive());
        }
        user.setUpdatedAt(now);
    }

}
