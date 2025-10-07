package com.Hub.organization.service;

import com.Hub.organization.dto.FunctionTypeAttributeCreateDTO;
import com.Hub.organization.dto.FunctionTypeAttributeResponseDTO;
import com.Hub.organization.exception.FunctionTypeAttributeAlreadyExistsException;
import com.Hub.organization.mapper.FunctionTypeAttributeMapper;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.repository.FunctionTypeAttributeRepository;
import com.Hub.organization.repository.FunctionTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FunctionTypeAttributeService {

    private final FunctionTypeAttributeRepository functionTypeAttributeRepository;
    private final FunctionTypeAttributeMapper functionTypeAttributeMapper;
    private final FunctionTypeRepository functionTypeRepository;

    public FunctionTypeAttributeService (FunctionTypeAttributeRepository functionTypeAttributeRepository, FunctionTypeAttributeMapper functionTypeAttributeMapper, FunctionTypeRepository functionTypeRepository) {
        this.functionTypeAttributeRepository = functionTypeAttributeRepository;
        this.functionTypeAttributeMapper = functionTypeAttributeMapper;
        this.functionTypeRepository = functionTypeRepository;
    }

    public ResponseEntity<FunctionTypeAttributeResponseDTO> addNewFunctionTypeAttribute (FunctionTypeAttributeCreateDTO createRequest) {

        Optional<FunctionTypeAttributeModel> existingAttr = functionTypeAttributeRepository.findByName(createRequest.name());
        if (existingAttr.isPresent()) {
            if (existingAttr.get().getFunctionType().getId() == createRequest.functionTypeId()) {
                throw new FunctionTypeAttributeAlreadyExistsException("An attribute with that name already exists for this function type.");
            }
        }
        FunctionTypeAttributeModel newFunctionTypeAttribute = functionTypeAttributeMapper.toModel(createRequest);
        newFunctionTypeAttribute.setFunctionType(functionTypeRepository.getReferenceById(createRequest.functionTypeId()));

        FunctionTypeAttributeModel savedFunctionTypeAttribute = functionTypeAttributeRepository.save(newFunctionTypeAttribute);

        FunctionTypeAttributeResponseDTO responseDTO = functionTypeAttributeMapper.toDTO(savedFunctionTypeAttribute);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
