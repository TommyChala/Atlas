package com.Hub.organization.service;

import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.model.FunctionTypeAttributeValueModel;
import com.Hub.organization.model.FunctionTypeInstanceModel;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.repository.FunctionTypeAttributeRepository;
import com.Hub.organization.repository.FunctionTypeInstanceRepository;
import com.Hub.organization.repository.FunctionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FunctionTypeInstanceService {

    @Autowired
    private FunctionTypeRepository functionTypeRepository;

    @Autowired
    private FunctionTypeInstanceRepository functionTypeInstanceRepository;

    @Autowired
    private FunctionTypeAttributeRepository functionTypeAttributeRepository;

    public FunctionTypeInstanceModel createFunctionTypeInstance (int functionTypeId, String name, Map<String, String> inputValues) {
        if (inputValues == null) {
            inputValues = Collections.emptyMap();
        }
        FunctionTypeModel functionType = functionTypeRepository.findById(functionTypeId)
                .orElseThrow(() -> new RuntimeException("FunctionTypeModel not found")
                );
        FunctionTypeInstanceModel newInstance = new FunctionTypeInstanceModel();
        newInstance.setName(name);
        newInstance.setFunctionType(functionType);

        List<FunctionTypeAttributeValueModel> valueModels = new ArrayList<>();
        for (FunctionTypeAttributeModel attribute : functionType.getAttributes()) {
            String inputValue = inputValues.get(attribute.getName());
            if (inputValue == null) {
                if (attribute.getIsRequired()) {
                    throw new IllegalArgumentException("Missing value for required attribute: " + attribute.getName());
                } else {
                continue;
                }
            }
            FunctionTypeAttributeValueModel valueModel = new FunctionTypeAttributeValueModel();
            valueModel.setInstance(newInstance);
            valueModel.setAttribute(attribute);
            valueModel.setValue(inputValue);

            valueModels.add(valueModel);
        }

        newInstance.setAttributeValues(valueModels);

        return functionTypeInstanceRepository.save(newInstance);

    }
}
