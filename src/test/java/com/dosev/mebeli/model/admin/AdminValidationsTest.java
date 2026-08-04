package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.dosev.mebeli.common.ApiPaths.ADMINS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminValidationsTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AdminService adminService;

    private static AdminRequest buildValidRequest() {
        AdminRequest request = new AdminRequest();
        request.setEmail("root@example.com");
        request.setPassword("s3cret!");
        request.setFirstName("Root");
        request.setLastName("Admin");
        return request;
    }

    @Test
    void createAdmin_blankEmail_returns400() throws Exception {
        AdminRequest request = buildValidRequest();
        request.setEmail(" ");

        mockMvc.perform(post(ADMINS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAdmin_invalidEmailFormat_returns400() throws Exception {
        AdminRequest request = buildValidRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post(ADMINS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAdmin_blankPassword_returns400() throws Exception {
        AdminRequest request = buildValidRequest();
        request.setPassword(" ");

        mockMvc.perform(post(ADMINS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
