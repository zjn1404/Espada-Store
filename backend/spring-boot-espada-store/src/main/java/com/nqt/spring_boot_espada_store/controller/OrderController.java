package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderDetailResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.service.order.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return new ApiResponse<>(orderResponse);
    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable("orderId") String orderId, @RequestBody OrderUpdateRequest request) {
        OrderResponse orderResponse = orderService.updateOrder(orderId, request);

        return new ApiResponse<>(orderResponse);
    }

    @GetMapping("/my-order")
    public ApiResponse<List<OrderResponse>> getMyOrders() {
        List<OrderResponse> orderResponses = orderService.getMyOrders();

        return new ApiResponse<>(orderResponses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable("userId") String userId) {
        List<OrderResponse> orderResponses = orderService.getOrderByUserId(userId);

        return new ApiResponse<>(orderResponses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orderResponses = orderService.getAllOrder();

        return new ApiResponse<>(orderResponses);
    }

    @GetMapping("/best-seller")
    public ApiResponse<List<ProductResponse>> getBestSellerOrders(@RequestParam("productAmount") int productAmount) {
        List<ProductResponse> productResponses = orderService.getBestSellers(PageRequest.of(0, productAmount));

        return new ApiResponse<>(productResponses);
    }

    @GetMapping("/order-detail/{orderId}")
    public ApiResponse<List<OrderDetailResponse>> getOrderDetail(@PathVariable("orderId") String orderId) {
        List<OrderDetailResponse> orderDetailResponses = orderService.getOrderDetails(orderId);

        return new ApiResponse<>(orderDetailResponses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ApiResponse<Object> deleteOrder(@PathVariable("orderId") String orderId) {
        orderService.deleteOrder(orderId);

        return new ApiResponse<>("Order deleted");
    }
}
