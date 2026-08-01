package com.dosev.mebeli.model.customer;

import com.dosev.mebeli.model.AbstractAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Table(name = "customers")
@SoftDelete(columnName = "is_deleted", strategy = SoftDeleteType.DELETED)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractAccount {
    @Column(name = "phone")
    private String phone;
}
