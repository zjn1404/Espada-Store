package com.nqt.spring_boot_espada_store.mapper;

import org.mapstruct.Mapper;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;
import com.nqt.spring_boot_espada_store.entity.Type;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    Type toType(TypeRequest request);

    TypeResponse toTypeResponse(Type type);
}
