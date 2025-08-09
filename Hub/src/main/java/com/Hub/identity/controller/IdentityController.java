package com.Hub.identity.controller;


import com.Hub.identity.dto.IdentityCreateDTO;
import com.Hub.identity.dto.IdentityModelRequest;
import com.Hub.identity.model.IdentityModel;
import com.Hub.identity.service.IdentityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController (IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping("/{id}")
    public IdentityModelRequest findById (@PathVariable UUID id) {
        return identityService.findById(id);
    }

    @PostMapping
    public ResponseEntity<IdentityCreateDTO> addNew(@RequestBody IdentityCreateDTO identityCreateDTO) {
        return identityService.addNew(identityCreateDTO);
    }
}
