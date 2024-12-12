package com.example.demo.dto;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    private Long clientId;
    private String name;
    private String designation;
    private boolean enabled;
}
