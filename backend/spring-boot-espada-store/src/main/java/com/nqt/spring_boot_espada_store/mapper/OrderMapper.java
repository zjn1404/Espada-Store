package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.OrderDetailResponse;
import com.nqt.spring_boot_espada_store.dto.response.OrderResponse;
import com.nqt.spring_boot_espada_store.entity.Order;
import com.nqt.spring_boot_espada_store.entity.OrderDetail;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "user", ignore = true)
    Order toOrder(OrderCreationRequest request);

    @Mapping(target = "userId", source = "user.id")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "productId", source = "id.productId")
    @Mapping(target = "size", source = "id.size")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    @Mapping(target = "state", ignore = true)
    void updateOrder(@MappingTarget Order order, OrderUpdateRequest request);
}
