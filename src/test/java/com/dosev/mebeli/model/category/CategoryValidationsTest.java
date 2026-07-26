package com.dosev.mebeli.model.category;

import com.dosev.mebeli.model.category.dto.CategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.CATEGORIES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void createCategory_blankName_returns400() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName(" ");

        mockMvc.perform(post(CATEGORIES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCategory_nameTooLong_returns400() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("a".repeat(51));

        mockMvc.perform(post(CATEGORIES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
