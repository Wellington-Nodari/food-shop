package com.application.food_shop.domain.staff.service;

import com.application.food_shop.domain.address.entity.Address;
import com.application.food_shop.domain.address.repository.AddressRepository;
import com.application.food_shop.domain.staff.entity.Staff;
import com.application.food_shop.domain.staff.enums.StaffPositions;
import com.application.food_shop.domain.staff.model.NewStaffDTO;
import com.application.food_shop.domain.staff.model.StaffDTO;
import com.application.food_shop.domain.staff.repository.StaffRepository;
import com.application.food_shop.domain.user.entity.User;
import com.application.food_shop.domain.user.enums.UserRole;
import com.application.food_shop.domain.user.repository.UserRepository;
import com.application.food_shop.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(StaffRepository staffRepository,  UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void newStaff (NewStaffDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        String encryptedPwd = passwordEncoder.encode(dto.getPassword());
        StaffPositions position = StaffPositions.valueOf(dto.getPosition().trim().toUpperCase());

        User user = new User(dto.getEmail(), encryptedPwd, UserRole.STAFF, true, now);
        userRepository.save(user);

        Address address = new Address(user, dto.getStreet(), dto.getNumber(), dto.getComplement(), dto.getCity(), dto.getPostalCode());
        addressRepository.save(address);

        staffRepository.save(new Staff(user, dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), position, now));
    }

    public StaffDTO findById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff with id " + id + " not found"));

        return new StaffDTO(staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getPosition(), staff.getPhoneNumber());
    }

    public StaffDTO findByName(String firstName, String lastName) {
        Staff staff = staffRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new ResourceNotFoundException("Staff with name " + firstName + " " + lastName + " was not found"));

        return new StaffDTO(staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getPosition(), staff.getPhoneNumber());
    }

    @Transactional
    public void updateStaff(Long id, StaffDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff with id " + id + " not found"));
        StaffPositions position = dto.getPosition();

        if (dto.getFirstName() != null) {
            staff.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            staff.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            staff.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPosition() != null) {
            staff.setPosition(position);
        }
        staff.setUpdatedAt(now);

        staffRepository.save(staff);
    }
}
