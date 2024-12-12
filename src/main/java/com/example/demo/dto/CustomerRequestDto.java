package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerRequestDto {
    String customerName;
    long clientId;
    String email;
    String phoneNumber;
    boolean enabled;
}
