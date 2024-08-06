package com.nqt.spring_boot_espada_store.service.customer;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerDetailResponse;

public interface CustomerDetailService {

    CustomerDetailResponse create(CustomerDetailCreationRequest request);

    CustomerDetailResponse getCustomerDetail();

    CustomerDetailResponse update(CustomerDetailUpdateRequest request);

}
