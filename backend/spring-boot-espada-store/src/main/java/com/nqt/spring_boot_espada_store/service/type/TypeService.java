package com.nqt.spring_boot_espada_store.service.type;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;

import java.util.List;

public interface TypeService {

    TypeResponse createType(TypeRequest request);

    TypeResponse getTypeById(String id);

    List<TypeResponse> getAllTypes();

    void deleteTypeById(String id);

}
