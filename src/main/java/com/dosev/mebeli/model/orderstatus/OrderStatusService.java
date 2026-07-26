package com.dosev.mebeli.model.orderstatus;

import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusRequest;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dosev.mebeli.common.EntityNames.ORDER_STATUS;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository repository;
    private final OrderStatusMapper mapper;

    public OrderStatusResponse createOrderStatus(OrderStatusRequest request){
        OrderStatus status = mapper.toEntity(request);
        status = repository.save(status);
        return mapper.toResponse(status);
    }

    public List<OrderStatusResponse> getAllOrderStatuses(){
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrderStatusResponse getOrderStatusById(Integer id){
        OrderStatus status = findStatusOrThrow(id);
        return mapper.toResponse(status);
    }

    public OrderStatusResponse updateOrderStatus(Integer id, OrderStatusRequest request){
        OrderStatus status = findStatusOrThrow(id);
        status = mapper.updateEntityFromDto(request, status);
        status = repository.save(status);
        return mapper.toResponse(status);
    }

    public void deleteOrderStatus(Integer id){
        repository.deleteById(id);
    }

    private OrderStatus findStatusOrThrow(Integer id){
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ORDER_STATUS, id)
                );
    }
}
