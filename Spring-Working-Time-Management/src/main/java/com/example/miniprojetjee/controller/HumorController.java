package com.example.miniprojetjee.controller;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Humor;
import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.repository.HumorRepo;
import com.example.miniprojetjee.repository.WorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/humor/add")
public class HumorController {

    private final HumorRepo humorRepo;
    private WorkRepo workRepo;
    private EmployeeRepo employeeRepo;

    @Autowired
    public HumorController(HumorRepo humorRepo,WorkRepo workRepo,EmployeeRepo employeeRepo) {
        this.humorRepo = humorRepo;
        this.workRepo = workRepo;
        this.employeeRepo = employeeRepo;
    }

    @PostMapping
    public ResponseEntity<Humor> addHumor(@RequestBody Humor humor, Principal principal) {
        Humor savedHumor;
        Work work = workRepo.findWorkByDateAndEmployee( LocalDate.now(),(Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        System.out.println(work.getIsEmpty());
        if(work.getIsEmpty()==false){
            work.setIsEmpty(true);
            workRepo.save(work);
            savedHumor=humorRepo.save(humor);
            return ResponseEntity.ok(savedHumor);
        }else{
            return ResponseEntity.badRequest().build();
        }

    }
}