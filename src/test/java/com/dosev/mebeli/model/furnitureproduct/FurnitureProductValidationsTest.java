package com.dosev.mebeli.model.furnitureproduct;

import com.dosev.mebeli.model.furnitureproduct.dto.FurnitureProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.dosev.mebeli.common.ApiPaths.FURNITURE_PRODUCTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FurnitureProductController.class)
class FurnitureProductValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FurnitureProductService furnitureProductService;

    private static FurnitureProductRequest buildValidRequest() {
        FurnitureProductRequest request = new FurnitureProductRequest();
        request.setTitle("Oslo Sofa");
        request.setPrice(new BigDecimal("499.99"));
        request.setDiscountPercentage((short) 10);
        request.setCategoryId(1);
        request.setFurnitureTypeId(1);
        request.setMaterialId(1);
        return request;
    }

    @Test
    void createFurnitureProduct_blankTitle_returns400() throws Exception {
        FurnitureProductRequest request = buildValidRequest();
        request.setTitle(" ");

        mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFurnitureProduct_priceMissing_returns400() throws Exception {
        FurnitureProductRequest request = buildValidRequest();
        request.setPrice(null);

        mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFurnitureProduct_priceNegative_returns400() throws Exception {
        FurnitureProductRequest request = buildValidRequest();
        request.setPrice(new BigDecimal("-1"));

        mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFurnitureProduct_discountOutOfRange_returns400() throws Exception {
        FurnitureProductRequest request = buildValidRequest();
        request.setDiscountPercentage((short) 101);

        mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFurnitureProduct_categoryIdMissing_returns400() throws Exception {
        FurnitureProductRequest request = buildValidRequest();
        request.setCategoryId(null);

        mockMvc.perform(post(FURNITURE_PRODUCTS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
