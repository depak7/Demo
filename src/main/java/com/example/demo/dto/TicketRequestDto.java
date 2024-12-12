package com.example.demo.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private long clientId;
    private String title;
    private String status;
}
