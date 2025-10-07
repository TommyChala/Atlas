package com.Hub.system.service;

import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.mapper.MappingConfigModelMapper;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Service;

@Service
public class MappingConfigModelService {

    private final IMappingConfigModelRepository mappingConfigModelRepository;
    private final MappingConfigModelMapper mappingConfigModelMapper;
    private final SystemRepository systemRepository;

    public MappingConfigModelService (IMappingConfigModelRepository mappingConfigModelRepository, MappingConfigModelMapper mappingConfigModelMapper, SystemRepository systemRepository) {
        this.mappingConfigModelRepository = mappingConfigModelRepository;
        this.mappingConfigModelMapper = mappingConfigModelMapper;
        this.systemRepository = systemRepository;
    }

    public MappingConfigModel addNew (MappingConfigModelCreateDTO mappingConfigModelCreateDTO) {
        MappingConfigModel mappingConfigModel = mappingConfigModelMapper.toModel(mappingConfigModelCreateDTO);
        SystemModel mappingSystem = systemRepository.findBySystemId(Long.valueOf(mappingConfigModelCreateDTO.systemId()))
                .orElseThrow(() -> new RuntimeException("Cannot find system")
                );
        mappingConfigModel.setSystem(mappingSystem);
        return mappingConfigModelRepository.save(mappingConfigModel);
    }
}
