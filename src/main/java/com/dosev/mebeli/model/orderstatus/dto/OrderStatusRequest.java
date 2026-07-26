package com.dosev.mebeli.model.orderstatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderStatusRequest {
    @NotBlank(message = "Status label is required")
    @Size(max = 20, message = "Status label must be at most 20 characters")
    private String statusName;
}
