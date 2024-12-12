package com.example.demo.dto;


import lombok.Data;
import java.util.Date;

@Data
public class TicketResponseDto {
    private int id;
    private String ticketCode;
    private String title;
    private String status;
    private Date lastModifiedDate;
}