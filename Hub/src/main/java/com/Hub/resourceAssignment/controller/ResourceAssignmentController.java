package com.Hub.resourceAssignment.controller;

import com.Hub.resourceAssignment.dto.ResourceAssignmentCreateDTO;
import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import com.Hub.resourceAssignment.service.ResourceAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource-assignment")
public class ResourceAssignmentController {

    private final ResourceAssignmentService resourceAssignmentService;

    public ResourceAssignmentController (ResourceAssignmentService resourceAssignmentService) {
        this.resourceAssignmentService = resourceAssignmentService;
    }

    @PostMapping
    public ResponseEntity<Void> createResourceAssignment (@RequestBody ResourceAssignmentCreateDTO createRequest) {
        resourceAssignmentService.createResourceAssignment(createRequest);

        return ResponseEntity.noContent().build();
    }
}
