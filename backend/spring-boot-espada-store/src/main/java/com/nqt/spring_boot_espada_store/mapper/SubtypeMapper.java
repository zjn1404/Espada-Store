package com.nqt.spring_boot_espada_store.mapper;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;
import com.nqt.spring_boot_espada_store.entity.Subtype;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubtypeMapper {

    @Mapping(target = "type", ignore = true)
    Subtype toSubType(SubtypeRequest request);

    SubtypeResponse toSubtypeResponse(Subtype subtype);

}
