package com.Hub.organization.controller;

import com.Hub.organization.dto.FunctionTypeInstanceCreateDTO;
import com.Hub.organization.service.FunctionTypeInstanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/FunctionTypeInstance")
public class FunctionTypeInstanceController {

    private final FunctionTypeInstanceService functionTypeInstanceService;

    public FunctionTypeInstanceController (FunctionTypeInstanceService functionTypeInstanceService) {
        this.functionTypeInstanceService = functionTypeInstanceService;
    }

    @PostMapping
    public ResponseEntity<String> addNewFunctionTypeInstance (@RequestBody FunctionTypeInstanceCreateDTO createRequest) {
        functionTypeInstanceService.createFunctionTypeInstance(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("New function type instance created!");
    }
}
