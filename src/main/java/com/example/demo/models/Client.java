package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String clientName;
    private String clientCode;

    @OneToMany(mappedBy = "client")
    private List<Employees> employees;

    @OneToMany(mappedBy = "client")
    private List<Customer> customers;

    private boolean enabled;
    private Date lastModifiedDate;
    private Date createdDate;

}
