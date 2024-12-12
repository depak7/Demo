package com.example.demo.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CustomerResponseDto {


    int id;
    int ClientId;

    String customerName;
    Date lastModifiedDate;

    Date createdDate;

    String PhoneNumber;
    String customerCode;

    String email;

    boolean enabled;
}
