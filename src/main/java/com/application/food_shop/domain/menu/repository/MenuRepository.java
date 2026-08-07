package com.application.food_shop.domain.menu.repository;

import com.application.food_shop.domain.menu.entity.Menu;
import com.application.food_shop.domain.menu.model.MenuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {


}
