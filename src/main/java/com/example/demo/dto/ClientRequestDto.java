package com.example.demo.dto;

import lombok.Data;
import lombok.NonNull;

@Data

public class ClientRequestDto {
    @NonNull
    private String clientName;
    @NonNull
    private String clientCode;
    @NonNull
    private boolean enabled;
}
