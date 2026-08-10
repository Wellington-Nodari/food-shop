package com.application.food_shop.domain.user.controller;

import com.application.food_shop.domain.user.model.UserDTO;
import com.application.food_shop.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable Long id,@Valid @RequestBody UserDTO dto){
        userService.updateUser(id, dto);
    }


}
