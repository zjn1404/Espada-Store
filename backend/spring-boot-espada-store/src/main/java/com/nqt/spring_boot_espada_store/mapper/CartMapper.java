package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {

    CartResponse toCartResponse(Cart cart);

}
