package com.nqt.spring_boot_espada_store.service.customer;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse create(CustomerCreationRequest request);

    CustomerResponse getByUser();

    CustomerResponse update(CustomerUpdateRequest request);

}
