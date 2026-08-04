package com.dosev.mebeli.model.customer;

import com.dosev.mebeli.model.customer.dto.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.CUSTOMERS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CustomerService customerService;

    private static CustomerRequest buildValidRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setEmail("jane@example.com");
        request.setPassword("s3cret!");
        request.setFirstName("Jane");
        request.setLastName("Doe");
        return request;
    }

    @Test
    void createCustomer_blankEmail_returns400() throws Exception {
        CustomerRequest request = buildValidRequest();
        request.setEmail(" ");

        mockMvc.perform(post(CUSTOMERS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_invalidEmailFormat_returns400() throws Exception {
        CustomerRequest request = buildValidRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post(CUSTOMERS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_blankPassword_returns400() throws Exception {
        CustomerRequest request = buildValidRequest();
        request.setPassword(" ");

        mockMvc.perform(post(CUSTOMERS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_blankFirstName_returns400() throws Exception {
        CustomerRequest request = buildValidRequest();
        request.setFirstName(" ");

        mockMvc.perform(post(CUSTOMERS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
