package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.category.Category;
import com.dosev.mebeli.model.category.CategoryRepository;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductRequest;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductResponse;
import com.dosev.mebeli.model.furnituretype.FurnitureType;
import com.dosev.mebeli.model.furnituretype.FurnitureTypeRepository;
import com.dosev.mebeli.model.material.Material;
import com.dosev.mebeli.model.material.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureProductService {

    private final FurnitureProductRepository repository;
    private final FurnitureProductMapper mapper;
    private final CategoryRepository categoryRepository;
    private final FurnitureTypeRepository furnitureTypeRepository;
    private final MaterialRepository materialRepository;

    public FurnitureProductResponse createFurnitureProduct(FurnitureProductRequest request) {
        FurnitureProduct furnitureProduct = mapper.toEntity(request);
        applyRelations(furnitureProduct, request);

        furnitureProduct = repository.save(furnitureProduct);
        return mapper.toResponse(furnitureProduct);
    }

    public List<FurnitureProductResponse> getAllFurnitureProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public FurnitureProductResponse getFurnitureProductById(Integer id) {
        FurnitureProduct furnitureProduct = findFurnitureProductOrThrow(id);
        return mapper.toResponse(furnitureProduct);
    }

    public FurnitureProductResponse updateFurnitureProduct(Integer id, FurnitureProductRequest request) {
        FurnitureProduct furnitureProduct = findFurnitureProductOrThrow(id);
        furnitureProduct = mapper.updateEntityFromDto(request, furnitureProduct);
        applyRelations(furnitureProduct, request);

        furnitureProduct = repository.save(furnitureProduct);
        return mapper.toResponse(furnitureProduct);
    }

    public void deleteFurnitureProduct(Integer id) {
        repository.deleteById(id);
    }

    private void applyRelations(FurnitureProduct furnitureProduct, FurnitureProductRequest request) {
        furnitureProduct.setCategory(findCategoryOrThrow(request.getCategoryId()));
        furnitureProduct.setFurnitureType(findFurnitureTypeOrThrow(request.getFurnitureTypeId()));
        furnitureProduct.setMaterial(findMaterialOrThrow(request.getMaterialId()));
    }

    private FurnitureProduct findFurnitureProductOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.FURNITURE_PRODUCT, id));
    }

    private Category findCategoryOrThrow(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CATEGORY, id));
    }

    private FurnitureType findFurnitureTypeOrThrow(Integer id) {
        return furnitureTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.FURNITURE_TYPE, id));
    }

    private Material findMaterialOrThrow(Integer id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.MATERIAL, id));
    }
}
