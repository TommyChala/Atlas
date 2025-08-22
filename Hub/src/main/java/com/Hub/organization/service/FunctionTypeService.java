package com.Hub.organization.service;

import com.Hub.organization.dto.FunctionTypeCreateDTO;
import com.Hub.organization.exception.FunctionTypeAlreadyExistsException;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.repository.FunctionTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class FunctionTypeService {

    private final FunctionTypeRepository functionTypeRepository;

    public FunctionTypeService (FunctionTypeRepository functionTypeRepository) {
        this.functionTypeRepository = functionTypeRepository;
    }

    public FunctionTypeModel createNewFunctionType (FunctionTypeCreateDTO createRequest) {

        if (functionTypeRepository.findByName(createRequest.name())
                .isPresent()){
            throw new FunctionTypeAlreadyExistsException("A Function Type with the name '" + createRequest.name() + "' already exists");
        }

        FunctionTypeModel newFunctionType = new FunctionTypeModel();
        newFunctionType.setName(createRequest.name());
        newFunctionType.setSystemDefined(false);
        return functionTypeRepository.save(newFunctionType);
    }
}
