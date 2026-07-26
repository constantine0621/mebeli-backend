package com.dosev.mebeli.model.furnituretype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "furniture_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureType {
    @Id
    private int id;
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    public String getTypeName(){
        return typeName.trim().toLowerCase();
    }
}
