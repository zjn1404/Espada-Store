package com.nqt.spring_boot_espada_store.service.order;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderDetailResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.entity.*;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.OrderMapper;
import com.nqt.spring_boot_espada_store.mapper.ProductMapper;
import com.nqt.spring_boot_espada_store.repository.OrderDetailRepository;
import com.nqt.spring_boot_espada_store.repository.OrderRepository;
import com.nqt.spring_boot_espada_store.repository.ProductRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImp implements OrderService{


    enum ORDER_STATE {
        DEFAULT_STATE("waiting-confirm"),
        PREPARING("preparing"),
        SHIPPING("shipping"),
        TO_CUSTOMER("to-customer"),
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
    ProductRepository productRepository;
    OrderMapper orderMapper;
    ProductMapper productMapper;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = getUser();

        Order order = orderMapper.toOrder(request);

        Date orderDate = new Date();
        order.setState(ORDER_STATE.DEFAULT_STATE.status);
        order.setOrderingDate(orderDate);
        order.setUser(user);
        order.setId(user.getId() + "-" + orderDate.toString().replaceAll(" ", "").trim());
        order.setOrderDetails(buildOrderDetails(order, request.getProductSizeQuantity()));
        if (null == order.getOrderDetails() || order.getOrderDetails().isEmpty()) {
            throw new AppException(ErrorCode.CART_NOT_EXISTED);
        }
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    private Set<OrderDetail> buildOrderDetails(Order order, Map<String, Map<String, Integer>> productSizeQuantity) {
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (Map.Entry<String, Map<String, Integer>> entry : productSizeQuantity.entrySet()) {
            String productId = entry.getKey();
            Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
            entry.getValue().forEach((key, value) -> {
                OrderDetail orderDetail = new OrderDetail(order, product, key, value);
                orderDetailRepository.save(orderDetail);
                orderDetails.add(orderDetail);
            });
        }
        return orderDetails;
    }

    @Override
    public OrderResponse updateOrder(String orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        orderMapper.updateOrder(order, request);
        String state = ORDER_STATE.valueOf(request.getState()).status;
        order.setState(state);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        User user = getUser();

        return orderRepository.findAllByUser(user).stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getOrderByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return orderRepository.findAllByUser(user).stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public Page<ProductResponse> getBestSellers(Pageable pageable) {
        Page<String> bestSellerIds = orderDetailRepository.findTop8BestSellers(pageable);
        List<ProductResponse> bestSellers = new ArrayList<>();
        bestSellerIds.forEach(bestSellerId ->
                bestSellers.add(productMapper.toProductResponse(productRepository.findById(bestSellerId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)))));

        return new PageImpl<>(bestSellers);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(String orderId) {
        return orderDetailRepository.findAllByOrderId(orderId).stream().map(orderMapper::toOrderDetailResponse).toList();
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
