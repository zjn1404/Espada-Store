package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderDetailResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.entity.Order;
import com.nqt.spring_boot_espada_store.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "user", ignore = true)
    Order toOrder(OrderCreationRequest request);

    OrderDetail toOrderDetail(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);

    OrderDetailResponse toOrderDetailResponse(Order order);

    void updateOrder(@MappingTarget Order order, OrderUpdateRequest request);
}
