package com.nqt.spring_boot_espada_store.service.customer;

import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.customer.CustomerUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.CustomerResponse;
import com.nqt.spring_boot_espada_store.entity.Customer;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.CustomerMapper;
import com.nqt.spring_boot_espada_store.repository.CustomerRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import com.nqt.spring_boot_espada_store.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImp implements CustomerService{

    CustomerRepository customerRepository;
    UserRepository userRepository;

    UserService userService;

    CustomerMapper customerMapper;

    @Override
    public CustomerResponse create(CustomerCreationRequest request) {
        UserCreationRequest customerCreationRequest = customerMapper.toUserCreationRequest(request);
        userService.createUser(customerCreationRequest);
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Customer customer = customerMapper.toCustomer(request);
        customer.setUser(user);

        String id = String.format("%s-info", user.getId());
        customer.setCustomerId(id);

        String userId = String.format("%s%s", user.getUsername(), user.getPhoneNumber());
        user.setId(userId);

        userRepository.save(user);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse getByUser() {

        User user = findUser();

        Customer customer = customerRepository.findByUser(user)
                .orElse(null);

        return Objects.isNull(customer) ? new CustomerResponse()
                : customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest request) {
        User user = findUser();

        Customer customer = customerRepository.findByUser(user)
                .orElse(null);

        if (Objects.isNull(customer)) {
            return new CustomerResponse();
        }

        UserUpdateRequest updateRequest = customerMapper.toUserUpdateRequest(request);

        userService.updateUser(updateRequest);

        customerMapper.updateCustomer(customer, request);
        customer.setUser(user);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    private User findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
