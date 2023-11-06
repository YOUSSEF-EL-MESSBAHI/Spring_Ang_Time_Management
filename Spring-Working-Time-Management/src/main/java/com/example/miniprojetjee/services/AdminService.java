package com.example.miniprojetjee.services;

import com.example.miniprojetjee.dto.EmployeeDto;
import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private EmployeeRepo employeeRepo;

    public AdminService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeDto> listEmployeesWithStatus() {
        List<Employee> employees = employeeRepo.findTop5ByOrderByIdAsc();
        LocalDate today = LocalDate.now();

        return employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),

                        getEmployeeStatusForToday(employee, today)
                ))
                .collect(Collectors.toList());
    }
    private int getEmployeeStatusForToday(Employee employee, LocalDate today) {
        return employee.getWorks().stream()
                .filter(work -> work.getDate().equals(today))
                .map(work -> work.isWorking() ? 1 : 0)
                .findFirst()
                .orElse(0);
    }
}
