package com.application.food_shop.domain.menu.controller;

import com.application.food_shop.domain.menu.entity.Menu;
import com.application.food_shop.domain.menu.model.MenuDTO;
import com.application.food_shop.domain.menu.service.MenuService;
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
    public Menu newMenu(@RequestBody Menu menu) {
        return menuService.newMenu(menu);
    }

    @PostMapping("/update")
    public void updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
    }

    @GetMapping("/all")
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }

}
