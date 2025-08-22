package com.Hub.organization.service;

import com.Hub.organization.dto.FunctionTypeInstanceCreateDTO;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.model.FunctionTypeAttributeValueModel;
import com.Hub.organization.model.FunctionTypeInstanceModel;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.repository.FunctionTypeAttributeRepository;
import com.Hub.organization.repository.FunctionTypeInstanceRepository;
import com.Hub.organization.repository.FunctionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FunctionTypeInstanceService {

    private final FunctionTypeRepository functionTypeRepository;

    private final FunctionTypeInstanceRepository functionTypeInstanceRepository;


    public FunctionTypeInstanceService (FunctionTypeRepository functionTypeRepository, FunctionTypeInstanceRepository functionTypeInstanceRepository) {
        this.functionTypeRepository = functionTypeRepository;
        this.functionTypeInstanceRepository = functionTypeInstanceRepository;
    }

    public FunctionTypeInstanceModel createFunctionTypeInstance (FunctionTypeInstanceCreateDTO createDTO) {

        Map<String, String> inputValues = createDTO.inputValues() != null ? createDTO.inputValues() : Collections.emptyMap();
        FunctionTypeModel functionType = functionTypeRepository.findById(createDTO.functionTypeId())
                .orElseThrow(() -> new EntityNotFoundException("FunctionTypeModel not found")
                );
        FunctionTypeInstanceModel newInstance = new FunctionTypeInstanceModel();
        newInstance.setName(createDTO.name());
        newInstance.setFunctionType(functionType);


        List<FunctionTypeAttributeValueModel> valueModels = new ArrayList<>();
        for (FunctionTypeAttributeModel attribute : functionType.getAttributes()) {
            String inputValue = inputValues.get(attribute.getName());
            if (inputValue == null && attribute.getIsRequired()) {
                throw new IllegalArgumentException("Missing value for required attribute: " + attribute.getName());
            }
            if (inputValue != null) {

                FunctionTypeAttributeValueModel valueModel = new FunctionTypeAttributeValueModel();
                valueModel.setInstance(newInstance);
                valueModel.setAttribute(attribute);
                valueModel.setValue(inputValue);

                valueModels.add(valueModel);
            }
        }

            newInstance.setAttributeValues(valueModels);


            return functionTypeInstanceRepository.save(newInstance);

        }
}
