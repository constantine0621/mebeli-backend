package com.dosev.mebeli.model.furnituretype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FurnitureTypeRequest {
    @NotBlank(message = "Type name is required")
    @Size(max = 50, message = "Type name must be at most 50 characters")
    private String typeName;
}
