package com.Hub.system.controller;

import com.Hub.system.dto.MappingConfigCreateBatchDTO;
import com.Hub.system.dto.MappingConfigCreateSingleDTO;
import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.service.MappingConfigModelService;
import com.Hub.system.utility.MappingConfigValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mappingConfigModel")
public class MappingConfigModelController {

    private final MappingConfigModelService mappingConfigModelService;
    private final MappingConfigValidator mappingConfigValidator;

    public MappingConfigModelController (MappingConfigModelService mappingConfigModelService, MappingConfigValidator mappingConfigValidator) {
        this.mappingConfigModelService = mappingConfigModelService;
        this.mappingConfigValidator = mappingConfigValidator;
    }

    @PostMapping("/create/batch")
    public ResponseEntity<Object> createBatch(
            @RequestBody MappingConfigCreateBatchDTO batchDto
    ) {
        mappingConfigValidator.validateMandatoryAttributes(batchDto.data(), batchDto.systemId());
        batchDto.data().forEach(
                mapping -> mappingConfigModelService.addNew(mapping, batchDto.systemId())
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> addNew (
            @RequestBody MappingConfigCreateSingleDTO mappingConfigCreateSingleDTO
            )
    {
        List<MappingConfigModelCreateDTO> list = List.of(mappingConfigCreateSingleDTO.mappingConfigModelCreateDTO());
        mappingConfigValidator.validateMandatoryAttributes(list, mappingConfigCreateSingleDTO.systemId());

        return ResponseEntity.ok().build();
    }
    
}
