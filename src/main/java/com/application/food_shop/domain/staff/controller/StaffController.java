package com.application.food_shop.domain.staff.controller;

import com.application.food_shop.domain.staff.model.NewStaffDTO;
import com.application.food_shop.domain.staff.model.StaffDTO;
import com.application.food_shop.domain.staff.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('STAFF')")
    public void newStaff (@Valid @RequestBody NewStaffDTO dto) {
        staffService.newStaff(dto);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StaffDTO findById(@PathVariable Long id) {
        return staffService.findById(id);
    }

    @GetMapping("/find/name")
    @PreAuthorize("hasRole('ADMIN')")
    public StaffDTO findByName(@RequestParam String firstName, @RequestParam String lastName) {
        return staffService.findByName(firstName, lastName);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public void updateStaff(@PathVariable Long id, @Valid @RequestBody StaffDTO dto) {
        staffService.updateStaff(id, dto);
    }

}
