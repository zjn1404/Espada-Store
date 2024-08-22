package com.nqt.spring_boot_espada_store.service.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderDetailResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;

public interface OrderService {

    OrderResponse createOrder(OrderCreationRequest request);

    OrderResponse updateOrder(String id, OrderUpdateRequest request);

    List<OrderResponse> getMyOrders();

    List<OrderResponse> getOrderByUserId(String userId);

    List<OrderResponse> getAllOrder();

    Page<ProductResponse> getBestSellers(Pageable pageable);

    List<OrderDetailResponse> getOrderDetails(String orderId);

    void deleteOrder(String orderId);
}
