package com.application.food_shop.domain.menu.service;

import com.application.food_shop.domain.menu.entity.Menu;
import com.application.food_shop.domain.menu.model.MenuDTO;
import com.application.food_shop.domain.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {

    MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu newMenu(Menu menu) {
        LocalDateTime now = LocalDateTime.now();
        menu.setCreatedAt(now);
        menu.setUpdatedAt(now);

        Double price = menu.getPrice();
        if(price == null || price.isNaN() || price < 0) {
            menu.setPrice(0.00);
        }

        Integer quantity = menu.getQuantity();
        if(quantity == null || quantity < 0) {
            menu.setQuantity(0);
        }

        return menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(Menu menu) {
        LocalDateTime now = LocalDateTime.now();
        menu.setUpdatedAt(now);

        convertToMenuDTO(menuRepository.save(menu));
    }

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    private MenuDTO convertToMenuDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setImage(menu.getImage());
        dto.setPrice(menu.getPrice());
        dto.setQuantity(menu.getQuantity());

        return dto;
    }

}
