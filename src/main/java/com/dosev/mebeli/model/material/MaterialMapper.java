package com.dosev.mebeli.model.material;

import com.dosev.mebeli.model.material.dto.MaterialRequest;
import com.dosev.mebeli.model.material.dto.MaterialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialResponse toResponse(Material material);

    @Mapping(target = "id", ignore = true)
    Material toEntity(MaterialRequest request);

    @Mapping(target = "id", ignore = true)
    Material updateEntityFromDto(MaterialRequest request, @MappingTarget Material material);
}
