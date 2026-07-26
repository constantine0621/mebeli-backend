package com.dosev.mebeli.model.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MaterialRequest {
    @NotBlank(message = "Material name is required")
    @Size(max = 50, message = "Material name must be at most 50 characters")
    private String materialName;
}
