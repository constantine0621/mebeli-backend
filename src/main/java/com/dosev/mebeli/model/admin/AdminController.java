package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.common.ApiPaths;
import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.admin.dto.AdminResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.ADMINS)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<AdminResponse> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminResponse getAdmin(@PathVariable UUID id) {
        return adminService.getAdminById(id);
    }

    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(adminService.createAdmin(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable UUID id, @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(adminService.updateAdmin(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable UUID id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
