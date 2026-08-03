package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.model.AbstractAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Admin extends AbstractAccount {
}
