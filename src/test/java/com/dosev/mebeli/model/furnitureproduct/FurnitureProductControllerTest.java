package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductRequest;
import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.FURNITURE_PRODUCTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FurnitureProductController.class)
class FurnitureProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FurnitureProductService furnitureProductService;

    private static FurnitureProductRequest buildRequest() {
        FurnitureProductRequest request = new FurnitureProductRequest();
        request.setTitle("Oslo Sofa");
        request.setDescription("Three-seat sofa");
        request.setPrice(new BigDecimal("499.99"));
        request.setDiscountPercentage((short) 10);
        request.setCategoryId(1);
        request.setFurnitureTypeId(1);
        request.setMaterialId(1);
        return request;
    }

    private static FurnitureProductResponse buildResponse(int id) {
        FurnitureProductResponse response = new FurnitureProductResponse();
        response.setId(id);
        response.setTitle("Oslo Sofa");
        response.setDescription("Three-seat sofa");
        response.setPrice(new BigDecimal("499.99"));
        response.setDiscountPercentage((short) 10);
        response.setCategoryId(1);
        response.setFurnitureTypeId(1);
        response.setMaterialId(1);
        return response;
    }

    @Test
    void getAllFurnitureProducts_returnsList() throws Exception {
        given(furnitureProductService.getAllFurnitureProducts()).willReturn(List.of(buildResponse(1)));

        MvcResult result = mockMvc.perform(get(FURNITURE_PRODUCTS))
                .andExpect(status().isOk())
                .andReturn();

        List<FurnitureProductResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, FurnitureProductResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getTitle()).isEqualTo("Oslo Sofa");
    }

    @Test
    void getFurnitureProduct_found_returnsProduct() throws Exception {
        given(furnitureProductService.getFurnitureProductById(1)).willReturn(buildResponse(1));

        MvcResult result = mockMvc.perform(get(FURNITURE_PRODUCTS+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureProductResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureProductResponse.class);
        assertThat(body.getPrice()).isEqualByComparingTo("499.99");
    }

    @Test
    void getFurnitureProduct_notFound_returns404() throws Exception {
        given(furnitureProductService.getFurnitureProductById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.FURNITURE_PRODUCT, 99));

        mockMvc.perform(get(FURNITURE_PRODUCTS+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createFurnitureProduct_valid_returnsCreated() throws Exception {
        given(furnitureProductService.createFurnitureProduct(any(FurnitureProductRequest.class))).willReturn(buildResponse(3));

        MvcResult result = mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureProductResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureProductResponse.class);
        assertThat(body.getId()).isEqualTo(3);
    }

    @Test
    void updateFurnitureProduct_found_returnsUpdated() throws Exception {
        given(furnitureProductService.updateFurnitureProduct(eq(4), any(FurnitureProductRequest.class))).willReturn(buildResponse(4));

        MvcResult result = mockMvc.perform(put(FURNITURE_PRODUCTS+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        FurnitureProductResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), FurnitureProductResponse.class);
        assertThat(body.getId()).isEqualTo(4);
    }

    @Test
    void deleteFurnitureProduct_found_returns204() throws Exception {
        mockMvc.perform(delete(FURNITURE_PRODUCTS+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(furnitureProductService).deleteFurnitureProduct(1);
    }

    @Test
    void getAllDeletedFurnitureProducts_returnsList() throws Exception {
        given(furnitureProductService.getAllDeletedFurnitureProducts()).willReturn(List.of(buildResponse(5)));

        mockMvc.perform(get(FURNITURE_PRODUCTS+"/deleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5));
    }
}
