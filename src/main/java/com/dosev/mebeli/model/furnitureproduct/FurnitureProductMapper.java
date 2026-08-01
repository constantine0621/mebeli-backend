package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductRequest;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FurnitureProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "furnitureType.id", target = "furnitureTypeId")
    @Mapping(source = "material.id", target = "materialId")
    FurnitureProductResponse toResponse(FurnitureProduct furnitureProduct);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "furnitureType", ignore = true)
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FurnitureProduct toEntity(FurnitureProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "furnitureType", ignore = true)
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FurnitureProduct updateEntityFromDto(FurnitureProductRequest request, @MappingTarget FurnitureProduct furnitureProduct);
}
