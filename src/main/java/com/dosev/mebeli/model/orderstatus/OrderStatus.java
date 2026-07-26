package com.dosev.mebeli.model.orderstatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus{
    @Id
    private int id;
    @Column(name = "status_name", nullable = false, length = 20)
    private String statusName;

    public String getStatusName() {
        return statusName.trim().toLowerCase();
    }
}
