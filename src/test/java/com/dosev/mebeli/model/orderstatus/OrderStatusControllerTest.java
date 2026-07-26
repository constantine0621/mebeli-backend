package com.dosev.mebeli.model.orderstatus;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusRequest;
import com.dosev.mebeli.model.orderstatus.dto.OrderStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dosev.mebeli.common.ApiPaths.ORDER_STATUSES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderStatusController.class)
class OrderStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private OrderStatusService orderStatusService;

    @Test
    void getAllOrderStatuses_returnsList() throws Exception {
        OrderStatusResponse response = new OrderStatusResponse();
        response.setId(1);
        response.setStatusName("pending");
        given(orderStatusService.getAllOrderStatuses()).willReturn(List.of(response));

        MvcResult result = mockMvc.perform(get(ORDER_STATUSES))
                .andExpect(status().isOk())
                .andReturn();

        List<OrderStatusResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, OrderStatusResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getId()).isEqualTo(1);
        assertThat(body.get(0).getStatusName()).isEqualTo("pending");
    }

    @Test
    void getOrderStatus_found_returnsStatus() throws Exception {
        OrderStatusResponse response = new OrderStatusResponse();
        response.setId(1);
        response.setStatusName("paid");
        given(orderStatusService.getOrderStatusById(1)).willReturn(response);

        MvcResult result = mockMvc.perform(get(ORDER_STATUSES+"/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        OrderStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), OrderStatusResponse.class);
        assertThat(body.getStatusName()).isEqualTo("paid");
    }

    @Test
    void getOrderStatus_notFound_returns404() throws Exception {
        given(orderStatusService.getOrderStatusById(99))
                .willThrow(new ResourceNotFoundException(EntityNames.ORDER_STATUS, 99));

        mockMvc.perform(get(ORDER_STATUSES+"/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createOrderStatus_valid_returnsCreated() throws Exception {
        OrderStatusRequest request = new OrderStatusRequest();
        request.setStatusName("shipped");

        OrderStatusResponse response = new OrderStatusResponse();
        response.setId(3);
        response.setStatusName("shipped");
        given(orderStatusService.createOrderStatus(any(OrderStatusRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(post(ORDER_STATUSES)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        OrderStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), OrderStatusResponse.class);
        assertThat(body.getId()).isEqualTo(3);
    }

    @Test
    void updateOrderStatus_found_returnsUpdated() throws Exception {
        OrderStatusRequest request = new OrderStatusRequest();
        request.setStatusName("delivered");

        OrderStatusResponse response = new OrderStatusResponse();
        response.setId(4);
        response.setStatusName("delivered");
        given(orderStatusService.updateOrderStatus(eq(4), any(OrderStatusRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(put(ORDER_STATUSES+"/{id}", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        OrderStatusResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), OrderStatusResponse.class);
        assertThat(body.getStatusName()).isEqualTo("delivered");
    }

    @Test
    void deleteOrderStatus_found_returns204() throws Exception {
        mockMvc.perform(delete(ORDER_STATUSES+"/{id}", 1))
                .andExpect(status().isNoContent());

        verify(orderStatusService).deleteOrderStatus(1);
    }
}
