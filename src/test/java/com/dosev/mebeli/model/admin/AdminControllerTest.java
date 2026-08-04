package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.admin.dto.AdminResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static com.dosev.mebeli.common.ApiPaths.ADMINS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AdminService adminService;

    private static AdminRequest buildRequest() {
        AdminRequest request = new AdminRequest();
        request.setEmail("root@example.com");
        request.setPassword("s3cret!");
        request.setFirstName("Root");
        request.setLastName("Admin");
        return request;
    }

    private static AdminResponse buildResponse(UUID id) {
        AdminResponse response = new AdminResponse();
        response.setId(id);
        response.setEmail("root@example.com");
        response.setFirstName("Root");
        response.setLastName("Admin");
        return response;
    }

    @Test
    void getAllAdmins_returnsList() throws Exception {
        UUID id = UUID.randomUUID();
        given(adminService.getAllAdmins()).willReturn(List.of(buildResponse(id)));

        MvcResult result = mockMvc.perform(get(ADMINS))
                .andExpect(status().isOk())
                .andReturn();

        List<AdminResponse> body = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AdminResponse.class));
        assertThat(body).hasSize(1);
        assertThat(body.get(0).getEmail()).isEqualTo("root@example.com");
    }

    @Test
    void getAdmin_found_returnsAdmin() throws Exception {
        UUID id = UUID.randomUUID();
        given(adminService.getAdminById(id)).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(get(ADMINS+"/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        AdminResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), AdminResponse.class);
        assertThat(body.getFirstName()).isEqualTo("Root");
    }

    @Test
    void getAdmin_notFound_returns404() throws Exception {
        UUID id = UUID.randomUUID();
        given(adminService.getAdminById(id))
                .willThrow(new ResourceNotFoundException(EntityNames.ADMIN, id));

        mockMvc.perform(get(ADMINS+"/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createAdmin_valid_returnsCreated() throws Exception {
        UUID id = UUID.randomUUID();
        given(adminService.createAdmin(any(AdminRequest.class))).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(post(ADMINS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        AdminResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), AdminResponse.class);
        assertThat(body.getId()).isEqualTo(id);
    }

    @Test
    void updateAdmin_found_returnsUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        given(adminService.updateAdmin(eq(id), any(AdminRequest.class))).willReturn(buildResponse(id));

        MvcResult result = mockMvc.perform(put(ADMINS+"/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk())
                .andReturn();

        AdminResponse body = objectMapper.readValue(result.getResponse().getContentAsString(), AdminResponse.class);
        assertThat(body.getId()).isEqualTo(id);
    }

    @Test
    void deleteAdmin_found_returns204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(ADMINS+"/{id}", id))
                .andExpect(status().isNoContent());

        verify(adminService).deleteAdmin(id);
    }
}
