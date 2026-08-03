package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.admin.dto.AdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminResponse toResponse(Admin admin);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Admin toEntity(AdminRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Admin updateEntityFromDto(AdminRequest request, @MappingTarget Admin admin);
}
