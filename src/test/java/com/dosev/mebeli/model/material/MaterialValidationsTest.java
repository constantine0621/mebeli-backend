package com.dosev.mebeli.model.material;

import com.dosev.mebeli.model.material.dto.MaterialRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.MATERIALS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaterialController.class)
class MaterialValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private MaterialService materialService;

    @Test
    void createMaterial_blankName_returns400() throws Exception {
        MaterialRequest request = new MaterialRequest();
        request.setMaterialName(" ");

        mockMvc.perform(post(MATERIALS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createMaterial_nameTooLong_returns400() throws Exception {
        MaterialRequest request = new MaterialRequest();
        request.setMaterialName("a".repeat(51));

        mockMvc.perform(post(MATERIALS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
