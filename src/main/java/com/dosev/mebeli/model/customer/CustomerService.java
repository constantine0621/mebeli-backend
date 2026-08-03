package com.dosev.mebeli.model.customer;

import com.dosev.mebeli.common.EntityNames;
import com.dosev.mebeli.common.exceptions.ResourceNotFoundException;
import com.dosev.mebeli.model.customer.dto.CustomerRequest;
import com.dosev.mebeli.model.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dosev.mebeli.common.config.WarningMessages.BAD_CREDENTIALS_WARNING;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final PasswordEncoder encoder;

    public Customer login (CustomerRequest request){
        Customer customer = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS_WARNING));

        if (!encoder.matches(request.getPassword(), customer.getPasswordHash())){
            throw new BadCredentialsException(BAD_CREDENTIALS_WARNING);
        }

        return customer;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        customer.setPasswordHash(
                encoder.encode(request.getPassword()
                ));

        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<CustomerResponse> getAllDeletedCustomers() {
        return repository.findAllDeleted()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CustomerResponse getCustomerById(UUID id) {
        Customer customer = findCustomerOrThrow(id);
        return mapper.toResponse(customer);
    }

    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        Customer customer = findCustomerOrThrow(id);
        customer = mapper.updateEntityFromDto(request, customer);
        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    public void deleteCustomer(UUID id) {
        repository.deleteById(id);
    }

    private Customer findCustomerOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CUSTOMER, id));
    }
}
