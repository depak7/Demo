package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    Client client;

    String name;
    String designation;
    String employeeCode;
    Date lastModifiedDate;
    boolean enabled;


}
