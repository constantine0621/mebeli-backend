package com.dosev.mebeli.model.furnituretype;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeRequest;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.FURNITURE_TYPES)
@RequiredArgsConstructor
public class FurnitureTypeController {

    private final FurnitureTypeService furnitureTypeService;

    @GetMapping
    public List<FurnitureTypeResponse> getAllFurnitureTypes() {
        return furnitureTypeService.getAllFurnitureTypes();
    }

    @GetMapping("/{id}")
    public FurnitureTypeResponse getFurnitureType(@PathVariable Integer id) {
        return furnitureTypeService.getFurnitureTypeById(id);
    }

    @PostMapping
    public ResponseEntity<FurnitureTypeResponse> createFurnitureType(@Valid @RequestBody FurnitureTypeRequest request) {
        return ResponseEntity.ok(furnitureTypeService.createFurnitureType(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FurnitureTypeResponse> updateFurnitureType(@PathVariable Integer id, @Valid @RequestBody FurnitureTypeRequest request) {
        return ResponseEntity.ok(furnitureTypeService.updateFurnitureType(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFurnitureType(@PathVariable Integer id) {
        furnitureTypeService.deleteFurnitureType(id);
        return ResponseEntity.noContent().build();
    }
}
