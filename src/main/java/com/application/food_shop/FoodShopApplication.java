package com.application.food_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FoodShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodShopApplication.class, args);
	}

}
