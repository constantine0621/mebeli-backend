package com.dosev.mebeli.model.orderstatus;

import com.dosev.mebeli.model.orderstatus.dto.OrderStatusRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderStatusController.class)
class OrderStatusValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private OrderStatusService orderStatusService;

    @Test
    void createOrderStatus_blankName_returns400() throws Exception {
        OrderStatusRequest request = new OrderStatusRequest();
        request.setStatusName(" ");

        mockMvc.perform(post("/api/order-statuses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrderStatus_nameTooLong_returns400() throws Exception {
        OrderStatusRequest request = new OrderStatusRequest();
        request.setStatusName("a".repeat(21));

        mockMvc.perform(post("/api/order-statuses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
