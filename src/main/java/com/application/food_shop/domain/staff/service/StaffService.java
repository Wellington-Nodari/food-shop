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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    public void newStaff (NewStaffDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        String encryptedPwd = passwordEncoder.encode(dto.getPassword());
        StaffPositions position = StaffPositions.valueOf(dto.getPosition());

        User user = new User(dto.getEmail(), encryptedPwd, UserRole.STAFF, true, now);
        userRepository.save(user);

        Address address = new Address(user, dto.getStreet(), dto.getNumber(), dto.getComplement(), dto.getCity(), dto.getPostalCode());
        addressRepository.save(address);

        staffRepository.save(new Staff(user, dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), position, now));
    }

    public StaffDTO findById(@PathVariable Long id) {
        Staff st = staffRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));

        return new StaffDTO(st.getId(), st.getFirstName(), st.getLastName(), st.getPosition(), st.getPhoneNumber());
    }

    public StaffDTO findByName(String firstName, String lastName) {
        Staff st = staffRepository.findByFirstNameAndLastName(firstName, lastName);

        return new StaffDTO(st.getId(), st.getFirstName(), st.getLastName(), st.getPosition(), st.getPhoneNumber());
    }

    public void updateStaff(Long id, StaffDTO dto) {
        Staff st = staffRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
        StaffPositions position = dto.getPosition();

        if (dto.getFirstName() != null) {
            st.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            st.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            st.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPosition() != null) {
            st.setPosition(position);
        }

        staffRepository.save(st);
    }
}
