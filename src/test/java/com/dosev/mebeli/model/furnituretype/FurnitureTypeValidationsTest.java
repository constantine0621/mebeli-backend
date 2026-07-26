package com.dosev.mebeli.model.furnituretype;

import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.FURNITURE_TYPES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FurnitureTypeController.class)
class FurnitureTypeValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FurnitureTypeService furnitureTypeService;

    @Test
    void createFurnitureType_blankName_returns400() throws Exception {
        FurnitureTypeRequest request = new FurnitureTypeRequest();
        request.setTypeName(" ");

        mockMvc.perform(post(FURNITURE_TYPES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFurnitureType_nameTooLong_returns400() throws Exception {
        FurnitureTypeRequest request = new FurnitureTypeRequest();
        request.setTypeName("a".repeat(51));

        mockMvc.perform(post(FURNITURE_TYPES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
