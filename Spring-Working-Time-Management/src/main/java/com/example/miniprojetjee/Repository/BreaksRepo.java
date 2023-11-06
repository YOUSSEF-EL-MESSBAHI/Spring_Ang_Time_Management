package com.example.miniprojetjee.repository;

import com.example.miniprojetjee.entity.Breaks;
import com.example.miniprojetjee.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BreaksRepo extends JpaRepository<Breaks, Long> {
    List<Breaks> findByDateAndWork(LocalDate date, Work work);
}
