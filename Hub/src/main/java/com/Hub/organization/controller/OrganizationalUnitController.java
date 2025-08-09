package com.Hub.organization.controller;

import com.Hub.organization.dto.OrganizationalUnitCreateDTO;
import com.Hub.organization.model.OrganizationalUnitModel;
import com.Hub.organization.service.OrganizationalUnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orgunit")
public class OrganizationalUnitController {

    private final OrganizationalUnitService organizationalUnitService;

    public OrganizationalUnitController (OrganizationalUnitService organizationalUnitService) {
        this.organizationalUnitService = organizationalUnitService;
    }

    @GetMapping("/{id}")
    public OrganizationalUnitModel findById(@PathVariable UUID id) {
        return organizationalUnitService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> addNew(@RequestBody OrganizationalUnitCreateDTO createDTO) {
        organizationalUnitService.addNew(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created new Organizational Unit successfully");
    }
}
