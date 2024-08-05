package com.nqt.spring_boot_espada_store.service.type;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;
import com.nqt.spring_boot_espada_store.entity.Type;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.TypeMapper;
import com.nqt.spring_boot_espada_store.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeServiceImp implements TypeService{

    TypeRepository typeRepository;
    TypeMapper typeMapper;

    @Override
    public TypeResponse createType(TypeRequest request) {

        if (typeRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.TYPE_EXISTED);
        }

        Type type = typeMapper.toType(request);

        typeRepository.save(type);

        return typeMapper.toTypeResponse(type);
    }

    @Override
    public TypeResponse getTypeById(String id) {
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_EXISTED));

        return typeMapper.toTypeResponse(type);
    }

    @Override
    public List<TypeResponse> getAllTypes() {
       return typeRepository.findAll().stream()
               .map(typeMapper::toTypeResponse)
               .toList();
    }

    @Override
    public void deleteTypeById(String id) {
        typeRepository.deleteById(id);
    }
}
