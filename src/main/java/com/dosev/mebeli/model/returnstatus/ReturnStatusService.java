package com.dosev.mebeli.model.returnstatus;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusRequest;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnStatusService {

    private final ReturnStatusRepository repository;
    private final ReturnStatusMapper mapper;

    public ReturnStatusResponse createReturnStatus(ReturnStatusRequest request) {
        ReturnStatus returnStatus = mapper.toEntity(request);

        returnStatus = repository.save(returnStatus);
        return mapper.toResponse(returnStatus);
    }

    public List<ReturnStatusResponse> getAllReturnStatuses() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ReturnStatusResponse getReturnStatusById(Integer id) {
        ReturnStatus returnStatus = findReturnStatusOrThrow(id);
        return mapper.toResponse(returnStatus);
    }

    public ReturnStatusResponse updateReturnStatus(Integer id, ReturnStatusRequest request) {
        ReturnStatus returnStatus = findReturnStatusOrThrow(id);

        returnStatus = mapper.updateEntityFromDto(request, returnStatus);

        returnStatus = repository.save(returnStatus);
        return mapper.toResponse(returnStatus);
    }

    public void deleteReturnStatus(Integer id) {
        repository.deleteById(id);
    }

    private ReturnStatus findReturnStatusOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.RETURN_STATUS, id));
    }

}
