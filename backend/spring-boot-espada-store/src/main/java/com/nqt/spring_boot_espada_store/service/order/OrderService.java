package com.nqt.spring_boot_espada_store.service.order;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreationRequest request);

    OrderResponse updateOrder(OrderUpdateRequest request);

    OrderResponse getMyOrder();

    OrderResponse getOrderByUserId(String userId);

    List<OrderResponse> getAllOrder();

    List<ProductResponse> getBestSellers();

    void deleteOrder(String orderId);

}
