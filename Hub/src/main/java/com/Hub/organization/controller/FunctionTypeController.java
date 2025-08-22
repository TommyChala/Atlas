package com.Hub.organization.controller;

import com.Hub.organization.dto.FunctionTypeCreateDTO;
import com.Hub.organization.exception.FunctionTypeAlreadyExistsException;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.service.FunctionTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/functionType")
public class FunctionTypeController {

    private final FunctionTypeService functionTypeService;

    public FunctionTypeController (FunctionTypeService functionTypeService) {
        this.functionTypeService = functionTypeService;
    }

    @PostMapping
    public ResponseEntity<FunctionTypeModel> createNewFunctionType (
            @Valid @RequestBody FunctionTypeCreateDTO createRequest
    ) {
            FunctionTypeModel createdFunctionType = functionTypeService.createNewFunctionType(createRequest);
            return ResponseEntity.ok(createdFunctionType);
    }
}
