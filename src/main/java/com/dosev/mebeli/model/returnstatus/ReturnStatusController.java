package com.dosev.mebeli.model.returnstatus;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusRequest;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.RETURN_STATUSES)
@RequiredArgsConstructor
public class ReturnStatusController {

    private final ReturnStatusService returnStatusService;

    @GetMapping
    public List<ReturnStatusResponse> getAllReturnStatuses() {
        return returnStatusService.getAllReturnStatuses();
    }

    @GetMapping("/{id}")
    public ReturnStatusResponse getReturnStatus(@PathVariable Integer id) {
        return returnStatusService.getReturnStatusById(id);
    }

    @PostMapping
    public ResponseEntity<ReturnStatusResponse> createReturnStatus(@Valid @RequestBody ReturnStatusRequest request) {
        return ResponseEntity.ok(returnStatusService.createReturnStatus(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnStatusResponse> updateReturnStatus(@PathVariable Integer id, @Valid @RequestBody ReturnStatusRequest request) {
        return ResponseEntity.ok(returnStatusService.updateReturnStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReturnStatus(@PathVariable Integer id) {
        returnStatusService.deleteReturnStatus(id);
        return ResponseEntity.noContent().build();
    }
}
