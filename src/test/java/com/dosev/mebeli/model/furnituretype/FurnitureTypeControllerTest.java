package com.dosev.mebeli.model.furnituretype;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeRequest;
import com.dosev.mebeli.model.furnituretype.dto.FurnitureTypeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.FURNITURE_TYPES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FurnitureTypeController.class)
class FurnitureTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FurnitureTypeService furnitureTypeService;

    @Test
    void getAllFurnitureTypes_returnsList() throws Exception {
        FurnitureTypeResponse response = new FurnitureTypeResponse();
        response.setId(1);
        response.setTypeName("sofa");
        given(furnitureTypeService.getAllFurnitureTypes()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(FURNITURE_TYPES))
                .andExpect(status().isOk())
                .andReturn();

        List<FurnitureTypeResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, FurnitureTypeResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getTypeName()).isEqualTo("sofa");
    }

    @Test
    void getFurnitureType_found_returnsType() throws Exception {
        FurnitureTypeResponse response = new FurnitureTypeResponse();
        response.setId(1);
        response.setTypeName("chair");
        given(furnitureTypeService.getFurnitureTypeById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(FURNITURE_TYPES+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureTypeResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureTypeResponse.class);
        assertThat(body.getTypeName()).isEqualTo("chair");
    }

    @Test
    void getFurnitureType_notFound_returns404() throws Exception {
        given(furnitureTypeService.getFurnitureTypeById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.FURNITURE_TYPE, 99));

        mockMvc.perform(get(FURNITURE_TYPES+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createFurnitureType_valid_returnsCreated() throws Exception {
        FurnitureTypeRequest request = new FurnitureTypeRequest();
        request.setTypeName("desk");

        FurnitureTypeResponse response = new FurnitureTypeResponse();
        response.setId(5);
        response.setTypeName("desk");
        given(furnitureTypeService.createFurnitureType(any(FurnitureTypeRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(FURNITURE_TYPES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureTypeResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureTypeResponse.class);
        assertThat(body.getId()).isEqualTo(5);
    }

    @Test
    void updateFurnitureType_found_returnsUpdated() throws Exception {
        FurnitureTypeRequest request = new FurnitureTypeRequest();
        request.setTypeName("bench");

        FurnitureTypeResponse response = new FurnitureTypeResponse();
        response.setId(4);
        response.setTypeName("bench");
        given(furnitureTypeService.updateFurnitureType(eq(4), any(FurnitureTypeRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(FURNITURE_TYPES+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureTypeResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureTypeResponse.class);
        assertThat(body.getTypeName()).isEqualTo("bench");
    }

    @Test
    void deleteFurnitureType_found_returns204() throws Exception {
        mockMvc.perform(delete(FURNITURE_TYPES+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(furnitureTypeService).deleteFurnitureType(1);
    }
}
