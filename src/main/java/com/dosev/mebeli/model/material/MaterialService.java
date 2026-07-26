package com.dosev.mebeli.model.material;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.material.dto.MaterialRequest;
import com.dosev.mebeli.model.material.dto.MaterialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    public MaterialResponse createMaterial(MaterialRequest request) {
        Material material = mapper.toEntity(request);

        material = repository.save(material);
        return mapper.toResponse(material);
    }

    public List<MaterialResponse> getAllMaterials() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public MaterialResponse getMaterialById(Integer id) {
        Material material = findMaterialOrThrow(id);
        return mapper.toResponse(material);
    }

    public MaterialResponse updateMaterial(Integer id, MaterialRequest request) {
        Material material = findMaterialOrThrow(id);
        material = mapper.updateEntityFromDto(request, material);
        material = repository.save(material);
        return mapper.toResponse(material);
    }

    public void deleteMaterial(Integer id) {
        repository.deleteById(id);
    }

    private Material findMaterialOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.MATERIAL, id));
    }

}
