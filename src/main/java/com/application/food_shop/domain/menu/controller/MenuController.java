package com.application.food_shop.domain.menu.controller;

import com.application.food_shop.domain.menu.entity.Menu;
import com.application.food_shop.domain.menu.service.MenuService;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public Menu newMenu(@RequestBody Menu menu) {
        return menuService.newMenu(menu);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
    }

    @GetMapping("/all")
    @PermitAll
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }

}
