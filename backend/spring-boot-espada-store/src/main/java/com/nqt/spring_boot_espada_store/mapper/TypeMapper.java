package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;
import com.nqt.spring_boot_espada_store.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    Type toType(TypeRequest request);

    TypeResponse toTypeResponse(Type type);
}
