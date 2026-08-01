package com.dosev.mebeli.model.furnitureproduct.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FurnitureProductRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", message = "Price must be at least 0")
    private BigDecimal price;

    @Min(value = 0, message = "Discount percentage must be at least 0")
    @Max(value = 100, message = "Discount percentage must be at most 100")
    private short discountPercentage;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    @NotNull(message = "Furniture type is required")
    private Integer furnitureTypeId;

    @NotNull(message = "Material is required")
    private Integer materialId;
}
