package com.example.miniprojetjee.controller;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.repository.WorkRepo;
import com.example.miniprojetjee.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/work")
@CrossOrigin
public class WorkController {
    @Autowired
    private IWorkService workService;

    private WorkRepo workRepo;

    private EmployeeRepo employeeRepo;

    @Autowired
    public WorkController(WorkRepo workRepo,
                          EmployeeRepo employeeRepo){
        this.workRepo = workRepo;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public List<Work> getWorks(){
        return workService.getWorks();
    }

    @GetMapping("/status")
    public boolean getWorkStatus(Principal principal){

        Work work=workRepo.findWorkByDateAndEmployee(LocalDate.now(), (Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        if(work==null){
            return false;
        }else{
            return work.isWorking();
        }

    }
    @GetMapping("/exist")
    public boolean getWorkExist(Principal principal){
        Work work=workRepo.findWorkByDateAndEmployee(LocalDate.now(), (Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        if(work==null){
          return false;
        }else{
            return true;
        }
    }

    @PostMapping
    public void addWork(@RequestBody Work work) {
        workService.addWork(work);
    }

    @PostMapping("/create")
    public Long createWork(@RequestBody Work work,Principal principal) {
        work.setEmployee((Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        work.setStart(LocalTime.now()); // Set start time as the current time
        work.setEnd(null); // Set end time as null
        work.setWorking(true);
        Work savedWork = workRepo.save(work);
        return savedWork.getId();
    }


    @PutMapping("/end")
    public ResponseEntity<?> endWork(Principal principal) {
        Work work=workRepo.findWorkByDateAndEmployee(LocalDate.now(), (Employee) employeeRepo.findByEmail(principal.getName()).orElse(null));
        long workId=work.getId();
        System.out.println(workId);
        Work existingWork = workRepo.findById(workId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found with id: " + workId));
        existingWork.setEnd(LocalTime.now());
        existingWork.setWorking(false);

        workRepo.save(existingWork);

        return ResponseEntity.ok().build();
    }
}
