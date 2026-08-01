package com.dosev.mebeli.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.sql.Timestamp;

@Data
@MappedSuperclass
public abstract class AbstractAuditableEntity {
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
