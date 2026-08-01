package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.model.AbstractBaseEntity;
import com.dosev.mebeli.model.category.Category;
import com.dosev.mebeli.model.furnituretype.FurnitureType;
import com.dosev.mebeli.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.math.BigDecimal;

@Entity
@Table(name = "furniture_products")
@SoftDelete(columnName = "is_deleted", strategy = SoftDeleteType.DELETED)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureProduct extends AbstractBaseEntity {

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount_percentage", nullable = false)
    private short discountPercentage;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "furniture_type_id", nullable = false)
    private FurnitureType furnitureType;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}
