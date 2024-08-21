package com.nqt.spring_boot_espada_store.controller;

import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.CustomerDetailResponse;
import com.nqt.spring_boot_espada_store.service.customer.CustomerDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/customer-info")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Customer Controller")
public class CustomerController {

    CustomerDetailService customerService;

    @PostMapping
    @Operation(
            summary = "Create customer's information detail",
            description = "API creates customer's information detail.")
    public ApiResponse<CustomerDetailResponse> createCustomer(@RequestBody CustomerDetailCreationRequest request) {
        return new ApiResponse<>(customerService.create(request));
    }

    @GetMapping
    @Operation(
            summary = "Get customer's information detail of logged-in user",
            description = "API gets customer's information detail of logged-in user.")
    public ApiResponse<CustomerDetailResponse> getCustomerDetailByUser() {
        return new ApiResponse<>(customerService.getCustomerDetail());
    }

    @PutMapping
    @Operation(
            summary = "Update customer's information detail of logged-in user",
            description = "API updates customer's information detail of logged-in user.")
    public ApiResponse<CustomerDetailResponse> updateCustomerDetail(@RequestBody CustomerDetailUpdateRequest request) {
        return new ApiResponse<>(customerService.update(request));
    }
}
