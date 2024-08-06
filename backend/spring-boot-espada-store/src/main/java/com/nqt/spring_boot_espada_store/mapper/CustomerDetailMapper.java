package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerDetailResponse;
import com.nqt.spring_boot_espada_store.entity.CustomerDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerDetailMapper {

    @Mapping(target = "user", ignore = true)
    CustomerDetail toCustomerDetail(CustomerDetailCreationRequest request);

    CustomerDetailResponse toCustomerDetailResponse(CustomerDetail customer);

    void updateCustomerDetail(@MappingTarget CustomerDetail customer, CustomerDetailUpdateRequest request);
}
