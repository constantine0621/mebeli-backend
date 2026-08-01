package com.dosev.mebeli.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractAccount extends AbstractAuditableEntity {
    @Id
    private UUID id;
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
}
