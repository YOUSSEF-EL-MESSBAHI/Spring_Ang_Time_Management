package com.example.miniprojetjee;

import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.example.miniprojetjee.services.EmailSenderService;
import com.example.miniprojetjee.services.EmployeeService;
import com.example.miniprojetjee.services.WorkService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class MiniProjetJeeApplication {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private WorkService workServices;



    Logger logger = Logger.getLogger(MiniProjetJeeApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(MiniProjetJeeApplication.class, args);
    }

    @Scheduled(cron = "0 50 17 * * MON-FRI")
    public void triggerMail() throws MessagingException {
        List<String> employeeEmails = workServices.getAllEmployeeEmails();

        for (String email : employeeEmails) {
            String subject = "Humor of the Day";

            String body = "Hello Sir/Madam,\n\n" +
                    "Please check the link below to add your humor of the day:\n" +
                    "http://localhost:4200/humor\n\n" +
                    "Thank you,\n" +
                    "From The Company Admin-Team";

            // Send the email
            senderService.sendSimpleEmail(email, subject, body);
 }
    }

    @Scheduled(cron="0 0 0 * * SAT")

    public void job() throws MessagingException, IOException {
        List<Employee> employees = employeeService.getAllEmployeesWithExactWorkDurationInLastSixDays();

        // Assuming employeePDFReport method returns ByteArrayInputStream
        ByteArrayInputStream bis = employeeService.employeePDFReport(employees);

        // Save the PDF to the specified location
        String path ="C:\\Users\\LENOVO\\Desktop\\prj\\Spring-Working-Time-Management\\src\\main\\resources\\templates\\employees.pdf";
        Path file = Paths.get(path);
        try (OutputStream os = Files.newOutputStream(file)) {
            bis.transferTo(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Employee employee : employees) {
            logger.info(employee.getFirstName());
        }

        String email = "youssef.abidi@etu.uae.ac.ma";
        String subject = "Weekly report";
        String body = "Dear Administrator,\n\n" +
                "We are pleased to attach the weekly employee report for your review.\n" +
                "Please find the attached file for your perusal.\n" +
                "Thank you for your attention to this matter.\n" +
                "Sincerely,\n" +
                "The Company System";

        senderService.sendEmailWithAttachment(email, subject, body,path);

    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner(
            EmployeeRepo service
    ) {

        return args -> {
            Employee employee= Employee.builder()
                    .email("elmessbahiyoussef@gmail.com")
                    .firstName("Youssef")
                    .lastName("Elmessbahi")
                    .jobTitle("Software Engineer")
                    .phone("0666666666")
                    .password(passwordEncoder.encode("admin"))
                    .roles("USER")
                    .build();
            Employee employe= Employee.builder()
                    .email("elmessbahiucef@gmail.com")
                    .firstName("Youssef")
                    .lastName("Elmessbahi")
                    .jobTitle("Software Engineer")
                    .phone("0666666666")
                    .password(passwordEncoder.encode("1234"))
                    .roles("ADMIN")
                    .build();
            service.save(employee);
            service.save(employe);

            System.out.println(employee.getEmail()+" _ "+employee.getPassword());
        };
    }

}
