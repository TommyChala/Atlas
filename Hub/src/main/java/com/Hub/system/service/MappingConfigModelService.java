package com.Hub.system.service;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.enums.MappingType;
import com.Hub.system.mapper.MappingConfigModelMapper;
import com.Hub.system.mapper.MappingExpressionMapper;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.MappingExpressionModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.repository.IMappingExpressionRepository;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MappingConfigModelService {

    private final IMappingConfigModelRepository mappingConfigModelRepository;
    private final MappingConfigModelMapper mappingConfigModelMapper;
    private final SystemRepository systemRepository;
    private final IAccountAttributeRepository accountAttributeRepository;
    private final MappingExpressionMapper mappingExpressionMapper;
    private final IMappingExpressionRepository mappingExpressionRepository;

    public MappingConfigModelService (
            IMappingConfigModelRepository mappingConfigModelRepository,
            MappingConfigModelMapper mappingConfigModelMapper,
            SystemRepository systemRepository,
            IAccountAttributeRepository accountAttributeRepository,
            MappingExpressionMapper mappingExpressionMapper,
            IMappingExpressionRepository mappingExpressionRepository
    ) {
        this.mappingConfigModelRepository = mappingConfigModelRepository;
        this.mappingConfigModelMapper = mappingConfigModelMapper;
        this.systemRepository = systemRepository;
        this.accountAttributeRepository = accountAttributeRepository;
        this.mappingExpressionMapper = mappingExpressionMapper;
        this.mappingExpressionRepository = mappingExpressionRepository;
    }

    public MappingConfigModel addNew (MappingConfigModelCreateDTO mappingConfigModelCreateDTO, String systemId) {
        MappingConfigModel mappingConfigModel = mappingConfigModelMapper.toModel(mappingConfigModelCreateDTO);

        SystemModel mappingSystem = systemRepository.findBySystemId(Long.valueOf(systemId))
                .orElseThrow(() -> new RuntimeException("Cannot find system")
                );
        AccountAttributeModel targetAttribute = accountAttributeRepository.findByName(mappingConfigModelCreateDTO.targetAttribute())
                        .orElseThrow(() -> new RuntimeException("Account attribute not found")
                        );

        mappingConfigModel.setSystem(mappingSystem);
        mappingConfigModel.setTargetAttribute(targetAttribute);
        MappingConfigModel newMappingConfig = mappingConfigModelRepository.save(mappingConfigModel);
        if (mappingConfigModelCreateDTO.mappingType().equalsIgnoreCase("Transformation") ||
            mappingConfigModelCreateDTO.mappingType().equalsIgnoreCase("CONSTANT")) {

            MappingExpressionModel mappingExpressionModel = mappingExpressionMapper.toModel(mappingConfigModelCreateDTO.mappingExpression());
            mappingExpressionModel.setActive(true);
            mappingExpressionModel.setMappingConfig(mappingConfigModel);
            mappingExpressionModel.setCreatedAt(LocalDateTime.now());
            mappingExpressionRepository.save(mappingExpressionModel);
        }
        return newMappingConfig;
    }
}
