package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductRequest;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.FURNITURE_PRODUCTS)
@RequiredArgsConstructor
public class FurnitureProductController {

    private final FurnitureProductService furnitureProductService;

    @GetMapping
    public List<FurnitureProductResponse> getAllFurnitureProducts() {
        return furnitureProductService.getAllFurnitureProducts();
    }

    @GetMapping("/{id}")
    public FurnitureProductResponse getFurnitureProduct(@PathVariable Integer id) {
        return furnitureProductService.getFurnitureProductById(id);
    }

    @PostMapping
    public ResponseEntity<FurnitureProductResponse> createFurnitureProduct(@Valid @RequestBody FurnitureProductRequest request) {
        return ResponseEntity.ok(furnitureProductService.createFurnitureProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FurnitureProductResponse> updateFurnitureProduct(@PathVariable Integer id, @Valid @RequestBody FurnitureProductRequest request) {
        return ResponseEntity.ok(furnitureProductService.updateFurnitureProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFurnitureProduct(@PathVariable Integer id) {
        furnitureProductService.deleteFurnitureProduct(id);
        return ResponseEntity.noContent().build();
    }
}
