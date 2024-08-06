package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerResponse;
import com.nqt.spring_boot_espada_store.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    UserUpdateRequest toUserUpdateRequest(CustomerUpdateRequest request);
    UserCreationRequest toUserCreationRequest(CustomerCreationRequest request);

    @Mapping(target = "user", ignore = true)
    Customer toCustomer(CustomerCreationRequest request);

    CustomerResponse toCustomerResponse(Customer customer);

    void updateCustomer(@MappingTarget Customer customer, CustomerUpdateRequest request);
}
