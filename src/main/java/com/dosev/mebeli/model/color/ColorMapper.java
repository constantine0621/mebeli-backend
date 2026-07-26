package com.dosev.mebeli.model.color;

import com.dosev.mebeli.model.color.dto.ColorRequest;
import com.dosev.mebeli.model.color.dto.ColorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorResponse toResponse(Color color);

    @Mapping(target = "id", ignore = true)
    Color toEntity(ColorRequest request);

    @Mapping(target = "id", ignore = true)
    Color updateEntityFromDto(ColorRequest request, @MappingTarget Color color);
}
