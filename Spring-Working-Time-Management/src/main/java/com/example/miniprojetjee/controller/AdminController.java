package com.example.miniprojetjee.controller;

import com.example.miniprojetjee.dto.EmployeeDto;
import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Humor;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.repository.HumorRepo;
import com.example.miniprojetjee.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private AdminService adminService;

    @Autowired
    private HumorRepo humorRepo;
    @GetMapping("/count-employees")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public long countAllEmployees() {
        return employeeRepo.countAllEmployees();
    }
    @GetMapping("/count-humors-negative")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public long countAllHumorsNegative() {
        return humorRepo.countAllHumorsNegative();
    }
    @GetMapping("/count-humors-positive")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public long countAllHumorsPositive() {
        return humorRepo.countAllHumorsPositive();
    }
    @GetMapping("/list-humors")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Humor> listHumors() {
        return humorRepo.findTop5ByOrderByIdAsc();
    }

    @GetMapping("/list-employees")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<EmployeeDto> listEmployees() {
        return adminService.listEmployeesWithStatus();
    }

}