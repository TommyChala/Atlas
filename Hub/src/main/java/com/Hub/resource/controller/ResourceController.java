package com.Hub.resource.controller;

import com.Hub.resource.service.ResourceService;
import com.Hub.resource.model.ResourceModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<ResourceModel>> findAll() {
        List<ResourceModel> allResources = resourceService.findAll();
        return ResponseEntity.ok(allResources);

    }

    @PostMapping
    public ResponseEntity<ResourceModel> create(@RequestBody ResourceModel resourceModel) {
        return ResponseEntity.ok(resourceService.createNew(resourceModel));
    }

}
