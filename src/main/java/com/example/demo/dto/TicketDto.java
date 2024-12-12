package com.example.demo.dto;

import com.example.demo.models.Client;
import lombok.Data;

import java.util.Date;

@Data
public class TicketDto {
    int id;
    int clientId;

    int ticketCode;

    String title;
    Date lastModifiedDate;

    String status;
}
