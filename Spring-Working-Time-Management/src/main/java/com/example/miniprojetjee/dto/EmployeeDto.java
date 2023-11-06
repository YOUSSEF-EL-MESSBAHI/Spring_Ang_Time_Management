package com.example.miniprojetjee.dto;


import lombok.Value;

import java.io.Serializable;

@Value
public class EmployeeDto implements Serializable {
    String firstName;
    String lastName;
    String jobTitle;
    int isWorking;

    public EmployeeDto(String firstName, String lastName, String jobTitle, int isWorking) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.isWorking = isWorking;
    }

}
