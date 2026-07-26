package com.dosev.mebeli.model.color;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.color.dto.ColorRequest;
import com.dosev.mebeli.model.color.dto.ColorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository repository;
    private final ColorMapper mapper;

    public ColorResponse createColor(ColorRequest request) {
        Color color = mapper.toEntity(request);

        color = repository.save(color);
        return mapper.toResponse(color);
    }

    public List<ColorResponse> getAllColors() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ColorResponse getColorById(Integer id) {
        Color color = findColorOrThrow(id);
        return mapper.toResponse(color);
    }

    public ColorResponse updateColor(Integer id, ColorRequest request) {
        Color color = findColorOrThrow(id);

        color = mapper.updateEntityFromDto(request, color);

        color = repository.save(color);
        return mapper.toResponse(color);
    }

    public void deleteColor(Integer id) {
        repository.deleteById(id);
    }

    private Color findColorOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.COLOR, id));
    }

}
