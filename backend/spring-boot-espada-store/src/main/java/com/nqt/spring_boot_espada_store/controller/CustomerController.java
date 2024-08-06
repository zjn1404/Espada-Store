package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.CustomerDetailResponse;
import com.nqt.spring_boot_espada_store.service.customer.CustomerDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-info")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    CustomerDetailService customerService;

    @PostMapping
    public ApiResponse<CustomerDetailResponse> createCustomer(@RequestBody CustomerDetailCreationRequest request) {
        return new ApiResponse<>(customerService.create(request));
    }

    @GetMapping
    public ApiResponse<CustomerDetailResponse> getCustomerByUser() {
        return new ApiResponse<>(customerService.getCustomerDetail());
    }

    @PutMapping
    public ApiResponse<CustomerDetailResponse> updateCustomer(@RequestBody CustomerDetailUpdateRequest request) {
        return new ApiResponse<>(customerService.update(request));
    }

}
