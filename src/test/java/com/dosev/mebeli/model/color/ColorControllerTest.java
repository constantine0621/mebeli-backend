package com.dosev.mebeli.model.color;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.color.dto.ColorRequest;
import com.dosev.mebeli.model.color.dto.ColorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.COLORS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColorController.class)
class ColorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ColorService colorService;

    @Test
    void getAllColors_returnsList() throws Exception {
        ColorResponse response = new ColorResponse();
        response.setId(1);
        response.setColorName("tan");
        given(colorService.getAllColors()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(COLORS))
                .andExpect(status().isOk())
                .andReturn();

        List<ColorResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ColorResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getColorName()).isEqualTo("tan");
    }

    @Test
    void getColor_found_returnsColor() throws Exception {
        ColorResponse response = new ColorResponse();
        response.setId(1);
        response.setColorName("olive");
        given(colorService.getColorById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(COLORS+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        ColorResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ColorResponse.class);
        assertThat(body.getColorName()).isEqualTo("olive");
    }

    @Test
    void getColor_notFound_returns404() throws Exception {
        given(colorService.getColorById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.COLOR, 99));

        mockMvc.perform(get(COLORS+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createColor_valid_returnsCreated() throws Exception {
        ColorRequest request = new ColorRequest();
        request.setColorName("charcoal");

        ColorResponse response = new ColorResponse();
        response.setId(3);
        response.setColorName("charcoal");
        given(colorService.createColor(any(ColorRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(COLORS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ColorResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ColorResponse.class);
        assertThat(body.getId()).isEqualTo(3);
    }

    @Test
    void updateColor_found_returnsUpdated() throws Exception {
        ColorRequest request = new ColorRequest();
        request.setColorName("emerald");

        ColorResponse response = new ColorResponse();
        response.setId(4);
        response.setColorName("emerald");
        given(colorService.updateColor(eq(4), any(ColorRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(COLORS+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ColorResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ColorResponse.class);
        assertThat(body.getColorName()).isEqualTo("emerald");
    }

    @Test
    void deleteColor_found_returns204() throws Exception {
        mockMvc.perform(delete(COLORS+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(colorService).deleteColor(1);
    }
}
