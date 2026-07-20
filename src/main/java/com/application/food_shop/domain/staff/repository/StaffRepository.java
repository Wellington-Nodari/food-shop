package com.application.food_shop.domain.staff.repository;

import com.application.food_shop.domain.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

    Optional<Staff> findByFirstNameAndLastName(String firstName, String lastName);

}
