package com.nqt.spring_boot_espada_store.service.type;

import java.util.List;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;

public interface TypeService {

    TypeResponse createType(TypeRequest request);

    TypeResponse getTypeById(String id);

    List<TypeResponse> getAllTypes();

    void deleteTypeById(String id);
}
