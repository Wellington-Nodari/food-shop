package com.application.food_shop.domain.customer.repository;

import com.application.food_shop.domain.customer.entity.Customer;
import com.application.food_shop.domain.customer.model.CustomerDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
