package com.Hub.system.controller;

import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.service.MappingConfigModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mappingConfigModel")
public class MappingConfigModelController {

    private final MappingConfigModelService mappingConfigModelService;

    public MappingConfigModelController (MappingConfigModelService mappingConfigModelService) {
        this.mappingConfigModelService = mappingConfigModelService;
    }

    @PostMapping
    public MappingConfigModel addNew (@RequestBody MappingConfigModelCreateDTO mappingConfigModelCreateDTO) {
        return mappingConfigModelService.addNew(mappingConfigModelCreateDTO);
    }
}
