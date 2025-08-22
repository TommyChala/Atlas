package com.Hub.organization.dto;

import jakarta.validation.constraints.NotBlank;

public record FunctionTypeCreateDTO(
        @NotBlank(message = "Name is required")
        String name
        ){}
