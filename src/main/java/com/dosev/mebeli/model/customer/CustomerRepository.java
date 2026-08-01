package com.dosev.mebeli.model.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);

    @Query(value = "SELECT * FROM customers WHERE is_deleted = true", nativeQuery = true)
    List<Customer> findAllDeleted();
}
