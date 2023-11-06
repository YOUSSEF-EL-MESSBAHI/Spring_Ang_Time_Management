package com.example.miniprojetjee.repository;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface WorkRepo extends JpaRepository<Work, Long> {
    @Query("SELECT e.email FROM Employee e")
    List<String> findAllEmails();

    @Query("SELECT w FROM Work w WHERE w.date = :date AND w.employee = :employee")
    Work findWorkByDateAndEmployee(@Param("date") LocalDate date, @Param("employee") Employee employee);

}
