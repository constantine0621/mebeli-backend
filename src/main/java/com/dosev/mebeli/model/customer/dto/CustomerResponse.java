package com.dosev.mebeli.model.customer.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CustomerResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
