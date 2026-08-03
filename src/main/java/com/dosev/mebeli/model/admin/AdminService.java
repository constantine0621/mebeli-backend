package com.dosev.mebeli.model.admin;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.admin.dto.AdminRequest;
import com.dosev.mebeli.model.admin.dto.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dosev.mebeli.common.config.WarningMessages.BAD_CREDENTIALS_WARNING;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder encoder;

    public Admin login(AdminRequest request) {
        Admin admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS_WARNING));

        if (!encoder.matches(request.getPassword(), admin.getPasswordHash())) {
            throw new BadCredentialsException(BAD_CREDENTIALS_WARNING);
        }

        return admin;
    }

    public AdminResponse createAdmin(AdminRequest request) {
        Admin admin = mapper.toEntity(request);
        admin.setPasswordHash(encoder.encode(request.getPassword()));

        admin = repository.save(admin);
        return mapper.toResponse(admin);
    }

    public List<AdminResponse> getAllAdmins() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AdminResponse getAdminById(UUID id) {
        Admin admin = findAdminOrThrow(id);
        return mapper.toResponse(admin);
    }

    public AdminResponse updateAdmin(UUID id, AdminRequest request) {
        Admin admin = findAdminOrThrow(id);
        admin = mapper.updateEntityFromDto(request, admin);
        admin = repository.save(admin);
        return mapper.toResponse(admin);
    }

    public void deleteAdmin(UUID id) {
        repository.deleteById(id);
    }

    private Admin findAdminOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.ADMIN, id));
    }
}
