package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    Client client;

    String customerName;
    Date lastModifiedDate;
    Date createdDate;
    String PhoneNumber;
    String customerCode;
    String email;
    boolean enabled;


}
