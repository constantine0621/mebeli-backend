package com.dosev.mebeli.model.category;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.category.dto.CategoryRequest;
import com.dosev.mebeli.model.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse getCategoryById(Integer id) {
        Category category = findCategoryOrThrow(id);
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = findCategoryOrThrow(id);

        category = categoryMapper.updateEntityFromDto(request, category);

        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryOrThrow(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CATEGORY, id));
    }
}
