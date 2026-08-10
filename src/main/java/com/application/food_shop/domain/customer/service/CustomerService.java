package com.application.food_shop.domain.customer.service;

import com.application.food_shop.domain.address.entity.Address;
import com.application.food_shop.domain.address.repository.AddressRepository;
import com.application.food_shop.domain.customer.entity.Customer;
import com.application.food_shop.domain.customer.model.CustomerDTO;
import com.application.food_shop.domain.customer.model.NewCustomerDTO;
import com.application.food_shop.domain.customer.repository.CustomerRepository;
import com.application.food_shop.domain.user.entity.User;
import com.application.food_shop.domain.user.enums.UserRole;
import com.application.food_shop.domain.user.repository.UserRepository;
import com.application.food_shop.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,  UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void newCustomer(NewCustomerDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        String encryptedPwd = passwordEncoder.encode(dto.getPassword());

        User user = new User(dto.getEmail(), encryptedPwd, UserRole.CUSTOMER, true, now);
        User newuser = userRepository.save(user);

        Address newAddress = new Address(newuser, dto.getStreet(), dto.getNumber(), dto.getComplement(),  dto.getCity(), dto.getPostalCode());
        addressRepository.save(newAddress);

        Customer newCustomer = new Customer(newuser, dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), LocalDateTime.now());
        customerRepository.save(newCustomer);
    }

    @Transactional
    public void updateCustomerByID (Long id, CustomerDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        Customer c = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            c.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            c.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()) {
            c.setPhoneNumber(dto.getPhoneNumber());
        }
        c.setUpdatedAt(now);

        customerRepository.save(c);
    }

    public CustomerDTO findCustomerById(Long id) {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        return new CustomerDTO(c.getId(), c.getFirstName(), c.getLastName(), c.getPhoneNumber());
    }
}
