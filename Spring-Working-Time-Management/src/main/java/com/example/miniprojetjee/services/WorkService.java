package com.example.miniprojetjee.services;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.repository.WorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService implements IWorkService{

    @Autowired
    private EmployeeRepo employeeRepository;
    @Autowired
    private  WorkRepo workRepo;


    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }



    public List<String> getAllEmployeeEmails() {
        return workRepo.findAllEmails();
}

    @Override
    public List<Work> getWorks() {
        return workRepo.findAll();
    }

    @Override
    public void addWork(Work work) {
        workRepo.save(work);
    }

    @Override
    public void updateWork(Work work) {
        workRepo.save(work);
    }
}
