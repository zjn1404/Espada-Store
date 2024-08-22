package com.nqt.spring_boot_espada_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;
import com.nqt.spring_boot_espada_store.entity.Subtype;

@Mapper(componentModel = "spring")
public interface SubtypeMapper {

    @Mapping(target = "type", ignore = true)
    Subtype toSubType(SubtypeRequest request);

    SubtypeResponse toSubtypeResponse(Subtype subtype);
}
