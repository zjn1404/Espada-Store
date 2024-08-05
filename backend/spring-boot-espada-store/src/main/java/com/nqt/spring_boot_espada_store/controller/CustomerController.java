package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.CustomerResponse;
import com.nqt.spring_boot_espada_store.service.customer.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody CustomerCreationRequest request) {
        return new ApiResponse<>(customerService.create(request));
    }

    @GetMapping
    public ApiResponse<CustomerResponse> getCustomerByUser() {
        return new ApiResponse<>(customerService.getByUser());
    }

    @PutMapping
    public ApiResponse<CustomerResponse> updateCustomer(@RequestBody CustomerUpdateRequest request) {
        return new ApiResponse<>(customerService.update(request));
    }

}
