package com.dosev.mebeli.model.furnituretype;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeRequest;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureTypeService {

    private final FurnitureTypeRepository repository;
    private final FurnitureTypeMapper mapper;

    public FurnitureTypeResponse createFurnitureType(FurnitureTypeRequest request) {
        FurnitureType furnitureType = mapper.toEntity(request);

        furnitureType = repository.save(furnitureType);
        return mapper.toResponse(furnitureType);
    }

    public List<FurnitureTypeResponse> getAllFurnitureTypes() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public FurnitureTypeResponse getFurnitureTypeById(Integer id) {
        FurnitureType furnitureType = findFurnitureTypeOrThrow(id);
        return mapper.toResponse(furnitureType);
    }

    public FurnitureTypeResponse updateFurnitureType(Integer id, FurnitureTypeRequest request) {
        FurnitureType furnitureType = findFurnitureTypeOrThrow(id);
        furnitureType = mapper.updateEntityFromDto(request, furnitureType);
        furnitureType = repository.save(furnitureType);
        return mapper.toResponse(furnitureType);

    }

    public void deleteFurnitureType(Integer id) {
        repository.deleteById(id);
    }

    private FurnitureType findFurnitureTypeOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.FURNITURE_TYPE, id));
    }

}
