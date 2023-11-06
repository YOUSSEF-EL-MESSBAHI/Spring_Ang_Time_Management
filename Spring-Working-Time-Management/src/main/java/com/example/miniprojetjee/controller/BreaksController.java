package com.example.miniprojetjee.controller;

import com.example.miniprojetjee.entity.Breaks;
import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.repository.BreaksRepo;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.repository.WorkRepo;
import com.example.miniprojetjee.services.IBreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/break")
@CrossOrigin
public class BreaksController {

    @Autowired
    private IBreakService breakService;

    private BreaksRepo breaksRepo;
    private EmployeeRepo employeeRepo;

    private WorkRepo workRepo;

    @Autowired
    public BreaksController(BreaksRepo breaksRepo, WorkRepo workRepo,EmployeeRepo employeeRepo){
        this.breaksRepo = breaksRepo;
        this.workRepo = workRepo;
        this.employeeRepo=employeeRepo;
    }

    @GetMapping
    public List<Breaks> getBreaks(Principal principal){
        Work work=workRepo.findWorkByDateAndEmployee(LocalDate.now(), (Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        return breakService.getBreaks(work);
    }

    @PostMapping
    public void addBreak(@RequestBody Breaks breaks, Principal principal) {
        breaks.setWork(workRepo.findWorkByDateAndEmployee(LocalDate.now(), (Employee) employeeRepo.findByEmail(principal.getName()).orElse(null)));
        breakService.addBreak(breaks);
    }



}
