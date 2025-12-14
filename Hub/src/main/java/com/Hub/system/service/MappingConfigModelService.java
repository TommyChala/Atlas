package com.Hub.system.service;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.mapper.MappingConfigModelMapper;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MappingConfigModelService {

    private final IMappingConfigModelRepository mappingConfigModelRepository;
    private final MappingConfigModelMapper mappingConfigModelMapper;
    private final SystemRepository systemRepository;
    private final IAccountAttributeRepository accountAttributeRepository;

    public MappingConfigModelService (IMappingConfigModelRepository mappingConfigModelRepository, MappingConfigModelMapper mappingConfigModelMapper, SystemRepository systemRepository, IAccountAttributeRepository accountAttributeRepository) {
        this.mappingConfigModelRepository = mappingConfigModelRepository;
        this.mappingConfigModelMapper = mappingConfigModelMapper;
        this.systemRepository = systemRepository;
        this.accountAttributeRepository = accountAttributeRepository;
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
        return mappingConfigModelRepository.save(mappingConfigModel);
    }
}
