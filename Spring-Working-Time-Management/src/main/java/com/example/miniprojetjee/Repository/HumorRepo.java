package com.example.miniprojetjee.repository;

import com.example.miniprojetjee.entity.Humor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HumorRepo extends JpaRepository<Humor, Long> {
    @Query("SELECT count(h) FROM Humor h WHERE h.rate < 3")
    long countAllHumorsNegative();
    @Query("SELECT count(h) FROM Humor h WHERE h.rate >= 3")
    long countAllHumorsPositive();

    List<Humor> findTop5ByOrderByIdAsc();
}
