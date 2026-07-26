package com.dosev.mebeli.model.color;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.color.dto.ColorRequest;
import com.dosev.mebeli.model.color.dto.ColorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.COLORS)
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public List<ColorResponse> getAllColors() {
        return colorService.getAllColors();
    }

    @GetMapping("/{id}")
    public ColorResponse getColor(@PathVariable Integer id) {
        return colorService.getColorById(id);
    }

    @PostMapping
    public ResponseEntity<ColorResponse> createColor(@Valid @RequestBody ColorRequest request) {
        return ResponseEntity.ok(colorService.createColor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorResponse> updateColor(@PathVariable Integer id, @Valid @RequestBody ColorRequest request) {
        return ResponseEntity.ok(colorService.updateColor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Integer id) {
        colorService.deleteColor(id);
        return ResponseEntity.noContent().build();
    }
}
