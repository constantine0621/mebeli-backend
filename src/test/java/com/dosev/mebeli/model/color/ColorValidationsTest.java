package com.dosev.mebeli.model.color;

import com.dosev.mebeli.model.color.dto.ColorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.COLORS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ColorController.class)
class ColorValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ColorService colorService;

    @Test
    void createColor_blankName_returns400() throws Exception {
        ColorRequest request = new ColorRequest();
        request.setColorName(" ");

        mockMvc.perform(post(COLORS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createColor_nameTooLong_returns400() throws Exception {
        ColorRequest request = new ColorRequest();
        request.setColorName("a".repeat(31));

        mockMvc.perform(post(COLORS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
