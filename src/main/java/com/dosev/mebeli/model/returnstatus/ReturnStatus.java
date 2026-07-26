package com.dosev.mebeli.model.returnstatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "return_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnStatus {
    @Id
    private int id;
    @Column(name = "status_name", nullable = false, length = 20)
    private String statusName;

    public String getStatusName(){
        return statusName.trim().toLowerCase();
    }
}
