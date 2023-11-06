package com.example.miniprojetjee.repository;

import com.example.miniprojetjee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Object> findByEmail(String username);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.works w LEFT JOIN FETCH w.breaks")
    List<Employee> findAllEmployeesWithWorksAndBreaks();

    @Query("SELECT COUNT(e) FROM Employee e")
    long countAllEmployees();

    //list employee limit 5
    List<Employee> findTop5ByOrderByIdAsc();
}
