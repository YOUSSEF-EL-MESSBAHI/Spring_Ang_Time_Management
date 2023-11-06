package com.example.miniprojetjee.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Duration duration;
    private boolean isWorking=false;
    private Duration breakDuration;
    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @OneToMany(mappedBy = "work",fetch = FetchType.EAGER)
    private List<Breaks> breaks;
    private boolean isEmpty;
    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Work(LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public Work(LocalDate date, LocalTime start) {
        this.date = date;
        this.start = start;
    }

    public Work(LocalDate date, LocalTime start, LocalTime end,
                Duration duration, Duration breakDuration, boolean isEmpty) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.breakDuration = breakDuration;
        this.isEmpty = isEmpty;
    }




}
