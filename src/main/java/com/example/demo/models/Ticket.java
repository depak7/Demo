package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    Client client;

    String ticketCode;

    String title;
    Date lastModifiedDate;

    String status;




}
