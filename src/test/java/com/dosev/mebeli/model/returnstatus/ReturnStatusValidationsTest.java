package com.dosev.mebeli.model.returnstatus;

import com.dosev.mebeli.model.returnstatus.dto.ReturnStatusRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.RETURN_STATUSES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReturnStatusController.class)
class ReturnStatusValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ReturnStatusService returnStatusService;

    @Test
    void createReturnStatus_blankName_returns400() throws Exception {
        ReturnStatusRequest request = new ReturnStatusRequest();
        request.setStatusName(" ");

        mockMvc.perform(post(RETURN_STATUSES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createReturnStatus_nameTooLong_returns400() throws Exception {
        ReturnStatusRequest request = new ReturnStatusRequest();
        request.setStatusName("a".repeat(21));

        mockMvc.perform(post(RETURN_STATUSES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
