package com.dosev.mebeli.model.customer;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.customer.dto.CustomerRequest;
import com.dosev.mebeli.model.customer.dto.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static com.dosev.mebeli.common.ApiPaths.CUSTOMERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CustomerService customerService;

    private static CustomerRequest buildRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setEmail("jane@example.com");
        request.setPassword("s3cret!");
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setPhone("555-1234");
        return request;
    }

    private static CustomerResponse buildResponse(UUID id) {
        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setEmail("jane@example.com");
        response.setFirstName("Jane");
        response.setLastName("Doe");
        response.setPhone("555-1234");
        return response;
    }

    @Test
    void getAllCustomers_returnsList() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.getAllCustomers()).willReturn(List.of(buildResponse(id)));

        MvcResult result = mockMvc.perform(get(CUSTOMERS))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, CustomerResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    void getCustomer_found_returnsCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.getCustomerById(id)).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(get(CUSTOMERS+"/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        CustomerResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponse.class);
        assertThat(body.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void getCustomer_notFound_returns404() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.getCustomerById(id))
                .willThrow(new ResourceNotFoundException(EntityNames.CUSTOMER, id));

        mockMvc.perform(get(CUSTOMERS+"/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createCustomer_valid_returnsCreated() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.createCustomer(any(CustomerRequest.class))).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(post(CUSTOMERS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        CustomerResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponse.class);
        assertThat(body.getId()).isEqualTo(id);
    }

    @Test
    void updateCustomer_found_returnsUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.updateCustomer(eq(id), any(CustomerRequest.class))).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(put(CUSTOMERS+"/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        CustomerResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponse.class);
        assertThat(body.getId()).isEqualTo(id);
    }

    @Test
    void deleteCustomer_found_returns204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(CUSTOMERS+"/{id}", id))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(id);
    }

    @Test
    void getAllDeletedCustomers_returnsList() throws Exception {
        UUID id = UUID.randomUUID();
        given(customerService.getAllDeletedCustomers()).willReturn(List.of(buildResponse(id)));

        mockMvc.perform(get(CUSTOMERS+"/deleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("jane@example.com"));
    }
}
