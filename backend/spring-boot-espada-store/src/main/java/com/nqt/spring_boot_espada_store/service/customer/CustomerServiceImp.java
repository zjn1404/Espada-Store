package com.nqt.spring_boot_espada_store.service.customer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerDetailUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerDetailResponse;
import com.nqt.spring_boot_espada_store.entity.CustomerDetail;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.CustomerDetailMapper;
import com.nqt.spring_boot_espada_store.repository.CustomerRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImp implements CustomerDetailService {

    CustomerRepository customerRepository;
    UserRepository userRepository;

    CustomerDetailMapper customerMapper;

    @Override
    public CustomerDetailResponse create(CustomerDetailCreationRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (customerRepository.existsById(user.getId())) {
            throw new AppException(ErrorCode.CUSTOMER_DETAIL_EXISTED);
        }
        CustomerDetail customerDetail = customerMapper.toCustomerDetail(request);
        customerDetail.setCustomerId(user.getId());
        customerRepository.save(customerDetail);

        return customerMapper.toCustomerDetailResponse(customerDetail);
    }

    @Override
    public CustomerDetailResponse getCustomerDetail() {
        User user = getUser();

        return customerMapper.toCustomerDetailResponse(user.getCustomerDetail());
    }

    @Override
    public CustomerDetailResponse update(CustomerDetailUpdateRequest request) {
        User user = getUser();
        CustomerDetail customerDetail = customerRepository
                .findById(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_DETAIL_NOT_EXISTED));

        customerMapper.updateCustomerDetail(customerDetail, request);
        customerRepository.saveAndFlush(customerDetail);

        return customerMapper.toCustomerDetailResponse(customerDetail);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
    }
}
