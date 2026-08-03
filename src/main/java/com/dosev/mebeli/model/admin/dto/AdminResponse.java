package com.dosev.mebeli.model.admin.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AdminResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
