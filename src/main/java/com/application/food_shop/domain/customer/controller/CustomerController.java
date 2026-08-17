package com.application.food_shop.domain.customer.controller;

import com.application.food_shop.domain.customer.model.CustomerDTO;
import com.application.food_shop.domain.customer.model.NewCustomerDTO;
import com.application.food_shop.domain.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/new")
    public void newCustomer(@Valid @RequestBody NewCustomerDTO dto) {
        customerService.newCustomer(dto);
    }

    @PutMapping("/update/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        customerService.updateCustomerByID(id, dto);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public CustomerDTO findCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }
}
