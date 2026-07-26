package com.dosev.mebeli.model.orderstatus;

import com.dosev.mebeli.model.orderstatus.dto.OrderStatusRequest;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatusResponse toResponse(OrderStatus orderStatus);

    @Mapping(target = "id", ignore = true)
    OrderStatus toEntity(OrderStatusRequest request);

    @Mapping(target = "id", ignore = true)
    OrderStatus updateEntityFromDto(OrderStatusRequest request,@MappingTarget OrderStatus status);
}
