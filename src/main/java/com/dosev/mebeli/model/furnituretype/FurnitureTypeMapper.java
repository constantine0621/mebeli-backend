package com.dosev.mebeli.model.furnituretype;

import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeRequest;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FurnitureTypeMapper {

    FurnitureTypeResponse toResponse(FurnitureType furnitureType);

    @Mapping(target = "id", ignore = true)
    FurnitureType toEntity(FurnitureTypeRequest request);

    @Mapping(target = "id", ignore = true)
    FurnitureType updateEntityFromDto(FurnitureTypeRequest request,@MappingTarget FurnitureType furnitureType);
}
