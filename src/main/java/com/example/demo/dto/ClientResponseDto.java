package com.example.demo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClientResponseDto {
    private int id;
    private String clientName;
    private String clientCode;
    private boolean enabled;
    private Date lastModifiedDate;
    private Date createdDate;
}
