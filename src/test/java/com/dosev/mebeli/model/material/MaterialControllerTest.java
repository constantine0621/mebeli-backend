package com.dosev.mebeli.model.material;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.material.dto.MaterialRequest;
import com.dosev.mebeli.model.material.dto.MaterialResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.MATERIALS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private MaterialService materialService;

    @Test
    void getAllMaterials_returnsList() throws Exception {
        MaterialResponse response = new MaterialResponse();
        response.setId(1);
        response.setMaterialName("velvet");
        given(materialService.getAllMaterials()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(MATERIALS))
                .andExpect(status().isOk())
                .andReturn();

        List<MaterialResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, MaterialResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getMaterialName()).isEqualTo("velvet");
    }

    @Test
    void getMaterial_found_returnsMaterial() throws Exception {
        MaterialResponse response = new MaterialResponse();
        response.setId(1);
        response.setMaterialName("solid oak");
        given(materialService.getMaterialById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(MATERIALS+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        MaterialResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), MaterialResponse.class);
        assertThat(body.getMaterialName()).isEqualTo("solid oak");
    }

    @Test
    void getMaterial_notFound_returns404() throws Exception {
        given(materialService.getMaterialById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.MATERIAL, 99));

        mockMvc.perform(get(MATERIALS+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createMaterial_valid_returnsCreated() throws Exception {
        MaterialRequest request = new MaterialRequest();
        request.setMaterialName("bouclé");

        MaterialResponse response = new MaterialResponse();
        response.setId(4);
        response.setMaterialName("bouclé");
        given(materialService.createMaterial(any(MaterialRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(MATERIALS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        MaterialResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), MaterialResponse.class);
        assertThat(body.getId()).isEqualTo(4);
    }

    @Test
    void updateMaterial_found_returnsUpdated() throws Exception {
        MaterialRequest request = new MaterialRequest();
        request.setMaterialName("walnut veneer");

        MaterialResponse response = new MaterialResponse();
        response.setId(5);
        response.setMaterialName("walnut veneer");
        given(materialService.updateMaterial(eq(5), any(MaterialRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(MATERIALS+"/{id}", 5)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        MaterialResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), MaterialResponse.class);
        assertThat(body.getMaterialName()).isEqualTo("walnut veneer");
    }

    @Test
    void deleteMaterial_found_returns204() throws Exception {
        mockMvc.perform(delete(MATERIALS+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(materialService).deleteMaterial(1);
    }
}
