package com.dosev.mebeli.model.category;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.category.dto.CategoryRequest;
import com.dosev.mebeli.model.category.dto.CategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.CATEGORIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void getAllCategories_returnsList() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1);
        response.setCategoryName("living room");
        given(categoryService.getAllCategories()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(CATEGORIES))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CategoryResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getCategoryName()).isEqualTo("living room");
    }

    @Test
    void getCategory_found_returnsCategory() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1);
        response.setCategoryName("kitchen");
        given(categoryService.getCategoryById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(CATEGORIES+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponse.class);
        assertThat(body.getCategoryName()).isEqualTo("kitchen");
    }

    @Test
    void getCategory_notFound_returns404() throws Exception {
        given(categoryService.getCategoryById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.CATEGORY, 99));

        mockMvc.perform(get(CATEGORIES+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Category not found with id: 99"));
    }

    @Test
    void createCategory_valid_returnsCreated() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("bedroom");

        CategoryResponse response = new CategoryResponse();
        response.setId(2);
        response.setCategoryName("bedroom");
        given(categoryService.createCategory(any(CategoryRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(CATEGORIES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponse.class);
        assertThat(body.getId()).isEqualTo(2);
    }

    @Test
    void updateCategory_found_returnsUpdated() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("office");

        CategoryResponse response = new CategoryResponse();
        response.setId(4);
        response.setCategoryName("office");
        given(categoryService.updateCategory(eq(4), any(CategoryRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(CATEGORIES+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponse.class);
        assertThat(body.getCategoryName()).isEqualTo("office");
    }

    @Test
    void deleteCategory_found_returns204() throws Exception {
        mockMvc.perform(delete(CATEGORIES+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(categoryService).deleteCategory(1);
    }
}
