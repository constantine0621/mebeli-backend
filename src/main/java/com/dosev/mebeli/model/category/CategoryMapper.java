package com.dosev.mebeli.model.category;

import com.dosev.mebeli.model.category.dto.CategoryRequest;
import com.dosev.mebeli.model.category.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequest request);

    @Mapping(target = "id", ignore = true)
    Category updateEntityFromDto(CategoryRequest request, @MappingTarget Category category);
}