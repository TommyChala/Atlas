package com.Hub.organization.controller;

import com.Hub.organization.dto.FunctionTypeAttributeCreateDTO;
import com.Hub.organization.dto.FunctionTypeAttributeResponseDTO;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.service.FunctionTypeAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/FunctionTypeAttribute")
public class FunctionTypeAttributeController {

    private final FunctionTypeAttributeService functionTypeAttributeService;

    public FunctionTypeAttributeController (FunctionTypeAttributeService functionTypeAttributeService) {
        this.functionTypeAttributeService = functionTypeAttributeService;
    }

    @PostMapping
    public ResponseEntity<FunctionTypeAttributeResponseDTO> addNewFunctionTypeAttribute (@RequestBody FunctionTypeAttributeCreateDTO createRequest) {
        return functionTypeAttributeService.addNewFunctionTypeAttribute(createRequest);
    }

}
