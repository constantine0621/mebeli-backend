package com.dosev.mebeli.model.material;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "materials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {
    @Id
    private int id;
    @Column(name = "material_name", nullable = false, length = 50)
    private String materialName;

    public String getMaterialName(){
        return this.materialName.trim().toLowerCase();
    }
}
