package com.example.demo.dto;

import lombok.Data;

import java.util.Date;
@Data
public class EmployeeResponseDto {

    int id;
    long clientId;
    String name;
    String designation;
    String employeeCode;
    Date lastModifiedDate;
    boolean enabled;
}
