package com.example.miniprojetjee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Breaks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;
}
