package com.dosev.mebeli.model.furnitureproduct.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FurnitureProductResponse {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private short discountPercentage;
    private Integer categoryId;
    private Integer furnitureTypeId;
    private Integer materialId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
