package com.dosev.mebeli.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbstractBaseEntity extends AbstractAuditableEntity {
    @Id
    private Integer id;
}
