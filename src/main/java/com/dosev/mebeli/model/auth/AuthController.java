package com.dosev.mebeli.model.auth;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.common.security.JwtService;
import com.dosev.mebeli.model.admin.Admin;
import com.dosev.mebeli.model.admin.AdminService;
import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.auth.dto.AuthResponse;
import com.dosev.mebeli.model.auth.dto.LoginRequest;
import com.dosev.mebeli.model.customer.Customer;
import com.dosev.mebeli.model.customer.CustomerService;
import com.dosev.mebeli.model.customer.dto.CustomerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AdminService adminService;
    private final JwtService jwtService;

    @PostMapping("/customer/login")
    public AuthResponse customerLogin(@Valid @RequestBody LoginRequest request) {
        CustomerRequest loginRequest = new CustomerRequest();
        loginRequest.setEmail(request.getEmail());
        loginRequest.setPassword(request.getPassword());

        Customer customer = customerService.login(loginRequest);
        String token = jwtService.generateToken(customer.getId().toString(), customer.getEmail(), "CUSTOMER");
        return new AuthResponse(token);
    }

    @PostMapping("/admin/login")
    public AuthResponse adminLogin(@Valid @RequestBody LoginRequest request) {
        AdminRequest loginRequest = new AdminRequest();
        loginRequest.setEmail(request.getEmail());
        loginRequest.setPassword(request.getPassword());

        Admin admin = adminService.login(loginRequest);
        String token = jwtService.generateToken(admin.getId().toString(), admin.getEmail(), "ADMIN");
        return new AuthResponse(token);
    }
}
