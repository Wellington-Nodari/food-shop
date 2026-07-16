package com.application.food_shop.domain.staff.repository;

import com.application.food_shop.domain.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

    Staff findByFirstNameAndLastName(String firstName, String lastName);

}
