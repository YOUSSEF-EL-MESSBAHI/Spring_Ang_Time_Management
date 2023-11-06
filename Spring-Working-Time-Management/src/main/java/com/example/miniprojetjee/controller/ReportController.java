package com.example.miniprojetjee.controller;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.services.EmailSenderService;
import com.example.miniprojetjee.services.EmployeeService;
import com.example.miniprojetjee.services.WorkService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    private WorkService workService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailSenderService senderService;


    @GetMapping("/report")
    public List<Employee> getEmployee() {
        return workService.getAllEmployee();
    }

    @GetMapping("/last-six-days-works-and-breaks")
    public List<Employee> getAllEmployeesWithWorksAndBreaksInLastSixDays() {
        return employeeService.getAllEmployeesWithWorksAndBreaksInLastSixDays();
    }

    @GetMapping("/last-six-days-works-duration")
    public List<Employee> getAllEmployeesWithExactWorkDurationInLastSixDays(){
        return employeeService.getAllEmployeesWithExactWorkDurationInLastSixDays();
    }

    @GetMapping(value = "/generatepdf/employees")
    public void generateEmployeeReport() throws IOException, MessagingException {
        List<Employee> employees = employeeService.getAllEmployeesWithExactWorkDurationInLastSixDays();

        // Assuming employeePDFReport method returns ByteArrayInputStream
        ByteArrayInputStream bis = employeeService.employeePDFReport(employees);

        // Save the PDF to the specified location
        Path file = Paths.get("D:\\Spring-Working-Time-Management\\src\\main\\resources\\templates\\employees.pdf");
        try (OutputStream os = Files.newOutputStream(file)) {
            bis.transferTo(os);
        }
        String email = "youssef.abidi@etu.uae.ac.ma";
        String subject = "Weekly report";
        String path ="D:\\Spring-Working-Time-Management\\src\\main\\resources\\templates\\employees.pdf";
        String body = "Dear Administrator,\n\n" +
                "We are pleased to attach the weekly employee report for your review.\n" +
                "Please find the attached file for your perusal.\n" +
                "Thank you for your attention to this matter.\n" +
                "Sincerely,\n" +
                "The Company System";

        senderService.sendEmailWithAttachment(email, subject, body,path);
    }

}
