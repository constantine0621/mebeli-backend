package com.dosev.mebeli.model.auth;

import com.dosev.mebeli.common.config.WarningMessages;
import com.dosev.mebeli.common.security.JwtService;
import com.dosev.mebeli.model.admin.Admin;
import com.dosev.mebeli.model.admin.AdminService;
import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.auth.dto.LoginRequest;
import com.dosev.mebeli.model.customer.Customer;
import com.dosev.mebeli.model.customer.CustomerService;
import com.dosev.mebeli.model.customer.dto.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static com.dosev.mebeli.common.ApiPaths.AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private JwtService jwtService;

    private static LoginRequest buildLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("jane@example.com");
        request.setPassword("s3cret!");
        return request;
    }

    @Test
    void customerLogin_valid_returnsToken() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setEmail("jane@example.com");
        given(customerService.login(any(CustomerRequest.class))).willReturn(customer);
        given(jwtService.generateToken(customer.getId().toString(), customer.getEmail(), "CUSTOMER"))
                .willReturn("customer-token");

        MvcResult result = mockMvc.perform(post(AUTH+"/customer/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildLoginRequest())))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("customer-token");
    }

    @Test
    void customerLogin_badCredentials_returns401() throws Exception {
        given(customerService.login(any(CustomerRequest.class)))
                .willThrow(new BadCredentialsException(WarningMessages.BAD_CREDENTIALS_WARNING));

        mockMvc.perform(post(AUTH+"/customer/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildLoginRequest())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void adminLogin_valid_returnsToken() throws Exception {
        Admin admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setEmail("jane@example.com");
        given(adminService.login(any(AdminRequest.class))).willReturn(admin);
        given(jwtService.generateToken(admin.getId().toString(), admin.getEmail(), "ADMIN"))
                .willReturn("admin-token");

        MvcResult result = mockMvc.perform(post(AUTH+"/admin/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildLoginRequest())))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("admin-token");
    }

    @Test
    void adminLogin_badCredentials_returns401() throws Exception {
        given(adminService.login(any(AdminRequest.class)))
                .willThrow(new BadCredentialsException(WarningMessages.BAD_CREDENTIALS_WARNING));

        mockMvc.perform(post(AUTH+"/admin/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildLoginRequest())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void customerLogin_blankEmail_returns400() throws Exception {
        LoginRequest request = buildLoginRequest();
        request.setEmail(" ");

        mockMvc.perform(post(AUTH+"/customer/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void customerLogin_blankPassword_returns400() throws Exception {
        LoginRequest request = buildLoginRequest();
        request.setPassword(" ");

        mockMvc.perform(post(AUTH+"/customer/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
