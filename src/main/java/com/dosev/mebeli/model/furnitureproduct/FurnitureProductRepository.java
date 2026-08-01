package com.dosev.mebeli.model.furnitureproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FurnitureProductRepository extends JpaRepository<FurnitureProduct, Integer> {
    @Query(value = "SELECT * FROM furniture_products WHERE is_deleted = true", nativeQuery = true)
    List<FurnitureProduct> findAllDeleted();
}
