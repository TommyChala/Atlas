package com.Hub.system.controller;

import com.Hub.system.model.SystemModel;
import com.Hub.system.service.SystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;

    public SystemController (SystemService systemService) {
        this.systemService = systemService;
    }

    @PostMapping
    public SystemModel addNew (@RequestBody SystemModel systemModel) {
        return systemService.addNew(systemModel);
    }
}
