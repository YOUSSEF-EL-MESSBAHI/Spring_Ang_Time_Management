package com.example.miniprojetjee.services;

import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.entity.Breaks;
import com.example.miniprojetjee.entity.Employee;
import com.example.miniprojetjee.repository.EmployeeRepo;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    public List<Employee> getAllEmployeesWithWorksAndBreaksInLastSixDays() {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> employeesUser = employees.stream().filter(employee -> employee.getRoles().equals("USER")).collect(Collectors.toList());
        LocalDate sixDaysAgo = LocalDate.now().minusDays(6);

        for (Employee employee : employees) {
            List<Work> filteredWorks = employee.getWorks().stream()
                    .filter(work -> {
                        LocalDate workDate = work.getDate();
                        return workDate.isAfter(sixDaysAgo);
                    })
                    .collect(Collectors.toList());

            for (Work work : filteredWorks) {
                List<Breaks> filteredBreaks = work.getBreaks().stream()
                        .filter(breaks -> {
                            LocalDate breakDate = breaks.getDate();
                            return breakDate.isAfter(sixDaysAgo);
                        })
                        .collect(Collectors.toList());
                work.setBreaks(filteredBreaks);

            }

            employee.setWorks(filteredWorks);
        }

        return employeesUser;
    }

    public List<Employee> getAllEmployeesWithExactWorkDurationInLastSixDays() {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> employeesUser = employees.stream().filter(employee -> employee.getRoles().equals("USER")).collect(Collectors.toList());
        LocalDate sixDaysAgo = LocalDate.now().minusDays(5);

        for (Employee employee : employees) {
            List<Work> filteredWorks = employee.getWorks().stream()
                    .filter(work -> {
                        LocalDate workDate = work.getDate();
                        return workDate.isAfter(sixDaysAgo);
                    })
                    .collect(Collectors.toList());

            for (Work work : filteredWorks) {
                List<Breaks> filteredBreaks = work.getBreaks().stream()
                        .filter(breaks -> {
                            LocalDate breakDate = breaks.getDate();
                            return breakDate.isAfter(sixDaysAgo);
                        })
                        .collect(Collectors.toList());

                Duration totalBreakDuration = Duration.ZERO;
                for (Breaks breaks : filteredBreaks) {
                    LocalTime breakStart = breaks.getStart();  // Assuming getStart() returns a LocalDateTime
                    LocalTime breakEnd = breaks.getEnd();  // Assuming getEnd() returns a LocalDateTime
                    totalBreakDuration = totalBreakDuration.plus(Duration.between(breakStart, breakEnd));
                    work.setBreakDuration(totalBreakDuration);
                }

                LocalTime workStart = work.getStart();
                LocalTime workEnd = work.getEnd();
                Duration workDuration = Duration.between(workStart, workEnd).minus(totalBreakDuration);

                // Assuming you have a method setDuration in the Work class, you can set the duration as follows
                work.setDuration(workDuration);
            }

            employee.setWorks(filteredWorks);
        }

        return employeesUser;
    }
    public static ByteArrayInputStream employeePDFReport(List<Employee> employees) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Content to PDF file ->
            Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            fontHeader.setStyle(Font.UNDERLINE);
            fontHeader.setColor(Color.RED);
            Paragraph para = new Paragraph("Employees weekly reports", fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            for (Employee employee : employees){
                Font fontHeader1 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 16);
                Paragraph para1 = new Paragraph("Weekly report for "+employee.getFirstName()+" "+employee.getLastName(), fontHeader1);
                para1.setAlignment(Element.ALIGN_CENTER);
                document.add(para1);
                document.add(Chunk.NEWLINE);
                PdfPTable table = new PdfPTable(6);
                // Add PDF Table Header ->
                Stream.of("Date", "Start Work", "End Work", "Break Duration", "Work Duration","Extra Work").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
                    header.setBackgroundColor(Color.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });
                List<Work> works = employee.getWorks();
                for (Work work : works) {
                    LocalDate localDate = work.getDate();
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    SimpleDateFormat formatter = new SimpleDateFormat("E dd MMMM yyyy");
                    String formattedDate = formatter.format(date);
                    System.out.println(localDate);
                    System.out.println(formattedDate);
                    PdfPCell idCell = new PdfPCell(new Phrase(formattedDate));
                    idCell.setPaddingLeft(4);
                    idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(idCell);

                    PdfPCell firstNameCell = new PdfPCell(new Phrase(String.valueOf(work.getStart())));
                    firstNameCell.setPaddingLeft(4);
                    firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    firstNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(firstNameCell);

                    PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(work.getEnd())));
                    lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    lastNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    lastNameCell.setPaddingRight(4);
                    table.addCell(lastNameCell);
                    Duration duration = work.getBreakDuration();
                    //System.out.println(duration + "Hello");
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    formatDuration(table, hours, minutes);

                    Duration durationWork = work.getDuration();
                    System.out.println(durationWork + "Hello");
                    long hours1 = durationWork.toHours();
                    long minutes1 = durationWork.toMinutes() % 60;
                    //String formattedDurationWork = String.format("%dH%dM", hours1, minutes1);
                    //PdfPCell phoneNumCell = new PdfPCell(new Phrase(formattedDurationWork));
                    //phoneNumCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    //phoneNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //phoneNumCell.setPaddingRight(4);
                    //table.addCell(phoneNumCell);
                    if (hours1 > 8) {
                        // Add an extra column with the extra work duration
                        long extraHours = hours1 - 8;
                        long normalWorkHours = hours1 - extraHours;
                        String formattedExtraWork = String.format("%dH%dM", extraHours, minutes1);
                        String formattedNormalWork = String.format("%dH%dM", normalWorkHours, 0);
                        PdfPCell phoneNumCell = new PdfPCell(new Phrase(formattedNormalWork));
                        phoneNumCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        phoneNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phoneNumCell.setPaddingRight(4);
                        PdfPCell extraWorkCell = new PdfPCell(new Phrase(formattedExtraWork));
                        extraWorkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        extraWorkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        extraWorkCell.setPaddingRight(4);

                        table.addCell(phoneNumCell); // Add the original work duration cell
                        table.addCell(extraWorkCell); // Add the extra work duration cell
                    } else {
                        formatDuration(table, hours1, minutes1);
                        String formattedExtraWork = String.format("%dH%dM", 0, 0);
                        PdfPCell extraWorkCell = new PdfPCell(new Phrase(formattedExtraWork));
                        extraWorkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        extraWorkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        extraWorkCell.setPaddingRight(4);
                        table.addCell(extraWorkCell);
                    }

                }
                document.add(table);

            }
            document.add(Chunk.NEWLINE);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void formatDuration(PdfPTable table, long hours1, long minutes1) {
        String formattedDurationWork = String.format("%dH%dM", hours1, minutes1);
        PdfPCell phoneNumCell = new PdfPCell(new Phrase(formattedDurationWork));
        phoneNumCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        phoneNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phoneNumCell.setPaddingRight(4);
        table.addCell(phoneNumCell);
    }

}