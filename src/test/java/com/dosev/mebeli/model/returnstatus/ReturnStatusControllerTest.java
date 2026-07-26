package com.dosev.mebeli.model.returnstatus;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusRequest;
import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.RETURN_STATUSES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReturnStatusController.class)
class ReturnStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ReturnStatusService returnStatusService;

    @Test
    void getAllReturnStatuses_returnsList() throws Exception {
        ReturnStatusResponse response = new ReturnStatusResponse();
        response.setId(1);
        response.setStatusName("requested");
        given(returnStatusService.getAllReturnStatuses()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(RETURN_STATUSES))
                .andExpect(status().isOk())
                .andReturn();

        List<ReturnStatusResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ReturnStatusResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getStatusName()).isEqualTo("requested");
    }

    @Test
    void getReturnStatus_found_returnsStatus() throws Exception {
        ReturnStatusResponse response = new ReturnStatusResponse();
        response.setId(1);
        response.setStatusName("approved");
        given(returnStatusService.getReturnStatusById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(RETURN_STATUSES+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        ReturnStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ReturnStatusResponse.class);
        assertThat(body.getStatusName()).isEqualTo("approved");
    }

    @Test
    void getReturnStatus_notFound_returns404() throws Exception {
        given(returnStatusService.getReturnStatusById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.RETURN_STATUS, 99));

        mockMvc.perform(get(RETURN_STATUSES+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createReturnStatus_valid_returnsCreated() throws Exception {
        ReturnStatusRequest request = new ReturnStatusRequest();
        request.setStatusName("rejected");

        ReturnStatusResponse response = new ReturnStatusResponse();
        response.setId(3);
        response.setStatusName("rejected");
        given(returnStatusService.createReturnStatus(any(ReturnStatusRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(RETURN_STATUSES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ReturnStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ReturnStatusResponse.class);
        assertThat(body.getId()).isEqualTo(3);
    }

    @Test
    void updateReturnStatus_found_returnsUpdated() throws Exception {
        ReturnStatusRequest request = new ReturnStatusRequest();
        request.setStatusName("completed");

        ReturnStatusResponse response = new ReturnStatusResponse();
        response.setId(4);
        response.setStatusName("completed");
        given(returnStatusService.updateReturnStatus(eq(4), any(ReturnStatusRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(RETURN_STATUSES+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ReturnStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), ReturnStatusResponse.class);
        assertThat(body.getStatusName()).isEqualTo("completed");
    }

    @Test
    void deleteReturnStatus_found_returns204() throws Exception {
        mockMvc.perform(delete(RETURN_STATUSES+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(returnStatusService).deleteReturnStatus(1);
    }
}
