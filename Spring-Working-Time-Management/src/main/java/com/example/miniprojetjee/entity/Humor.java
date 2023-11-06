package com.example.miniprojetjee.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Humor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String humor;
    private int rate;
    private LocalDate date;

    @PrePersist
    public void onPrePersist() {
        date = LocalDate.now();
    }
}