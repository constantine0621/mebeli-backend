package com.dosev.mebeli.model.orderstatus;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusRequest;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.ORDER_STATUSES)
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @PostMapping
    public ResponseEntity<OrderStatusResponse> createOrderStatus(@Valid @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(orderStatusService.createOrderStatus(request));
    }

    @GetMapping
    public List<OrderStatusResponse> getAllOrderStatuses() {
        return orderStatusService.getAllOrderStatuses();
    }

    @GetMapping("/{id}")
    public OrderStatusResponse getOrderStatus(@PathVariable Integer id) {
        return orderStatusService.getOrderStatusById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusResponse> updateOrderStatus(@PathVariable Integer id, @Valid @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(orderStatusService.updateOrderStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Integer id) {
        orderStatusService.deleteOrderStatus(id);
        return ResponseEntity.noContent().build();
    }
}
