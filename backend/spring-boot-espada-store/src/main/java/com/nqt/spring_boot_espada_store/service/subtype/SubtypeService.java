package com.nqt.spring_boot_espada_store.service.subtype;

import java.util.List;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;

public interface SubtypeService {

    SubtypeResponse create(SubtypeRequest request);

    SubtypeResponse getById(String id);

    List<SubtypeResponse> getAllSubtypes();

    List<SubtypeResponse> getAllSubtypesByType(String type);

    void delete(String id);
}
