package com.dosev.mebeli.model.returnstatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReturnStatusRequest {
    @NotBlank(message = "Status name is required")
    @Size(max = 20, message = "Status name must be at most 20 characters")
    private String statusName;
}
