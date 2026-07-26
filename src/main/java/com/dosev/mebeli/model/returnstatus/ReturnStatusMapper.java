package com.dosev.mebeli.model.returnstatus;

import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusRequest;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReturnStatusMapper {

    ReturnStatusResponse toResponse(ReturnStatus returnStatus);

    @Mapping(target = "id", ignore = true)
    ReturnStatus toEntity(ReturnStatusRequest request);

    @Mapping(target = "id", ignore = true)
    ReturnStatus updateEntityFromDto(ReturnStatusRequest request, @MappingTarget ReturnStatus returnStatus);
}
