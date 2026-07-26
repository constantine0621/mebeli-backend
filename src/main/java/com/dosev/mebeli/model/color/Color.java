package com.dosev.mebeli.model.color;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color {
    @Id
    private int id;
    @Column(name = "color_name", nullable = false, length = 30)
    private String colorName;

    public String getColorName(){
        return this.colorName.trim().toLowerCase();
    }
}
