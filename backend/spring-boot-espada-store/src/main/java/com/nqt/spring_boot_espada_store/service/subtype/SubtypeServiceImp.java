package com.nqt.spring_boot_espada_store.service.subtype;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;
import com.nqt.spring_boot_espada_store.entity.Subtype;
import com.nqt.spring_boot_espada_store.entity.Type;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.SubtypeMapper;
import com.nqt.spring_boot_espada_store.mapper.TypeMapper;
import com.nqt.spring_boot_espada_store.repository.SubtypeRepository;
import com.nqt.spring_boot_espada_store.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubtypeServiceImp implements SubtypeService{

    private static final Logger log = LoggerFactory.getLogger(SubtypeServiceImp.class);
    SubtypeRepository subtypeRepository;
    TypeRepository typeRepository;
    SubtypeMapper subtypeMapper;

    @Override
    public SubtypeResponse create(SubtypeRequest request) {

        Type type = typeRepository.findById(request.getType()).
                orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_EXISTED));

        Subtype subtype = subtypeMapper.toSubType(request);
        subtype.setType(type);

        subtypeRepository.save(subtype);

        return subtypeMapper.toSubtypeResponse(subtype);
    }

    @Override
    public SubtypeResponse getById(String id) {
        Subtype subtype = subtypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBTYPE_NOT_EXISTED));

        return subtypeMapper.toSubtypeResponse(subtype);
    }

    @Override
    public List<SubtypeResponse> getAllSubtypes() {
        return subtypeRepository.findAll().stream()
                .map(subtypeMapper::toSubtypeResponse)
                .toList();
    }

    @Override
    public List<SubtypeResponse> getAllSubtypesByType(String type) {

        Type foundType = typeRepository.findById(type)
                .orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_EXISTED));

        return subtypeRepository.findAllByType(foundType).stream()
                .map(subtypeMapper::toSubtypeResponse)
                .toList();
    }

    @Override
    public void delete(String id) {
        subtypeRepository.deleteById(id);
    }
}
