package com.nqt.spring_boot_espada_store.service.order;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.entity.Order;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.OrderMapper;
import com.nqt.spring_boot_espada_store.repository.OrderDetailRepository;
import com.nqt.spring_boot_espada_store.repository.OrderRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImp implements OrderService{


    enum ORDER_STATE {
        DEFAULT_STAE("waiting-confirm"),
        PREPARING("preparing"),
        SHIPPING("shipping"),
        TO_CUSTOMER("to_customer"),
        DELIVERED("delivered")
        ;

        final String status;

        ORDER_STATE(String status) {
            this.status = status;
        }
    }

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = getUser();

        Order order = orderMapper.toOrder(request);

        Date orderDate = new Date();
        order.setState(ORDER_STATE.DEFAULT_STAE.status);
        order.setOrderingDate(orderDate);
        order.setUser(user);
        order.setId(user.getId() + "-" + orderDate);


        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrder(OrderUpdateRequest request) {
        return null;
    }

    @Override
    public OrderResponse getMyOrder() {
        return null;
    }

    @Override
    public OrderResponse getOrderByUserId(String userId) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return List.of();
    }

    @Override
    public List<ProductResponse> getBestSellers() {
        return List.of();
    }

    @Override
    public void deleteOrder(String orderId) {

    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
