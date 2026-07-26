package com.dosev.mebeli.model.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private int id;
    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    public String getCategoryName(){
        return this.categoryName.trim().toLowerCase();
    }
}