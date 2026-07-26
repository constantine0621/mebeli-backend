package com.dosev.mebeli.model.color.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ColorRequest {
    @NotBlank(message = "Color name is required")
    @Size(max = 30, message = "Color name must be at most 30 characters")
    private String colorName;
}
