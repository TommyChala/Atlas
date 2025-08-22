package com.Hub.organization.dto;

import java.util.Map;

public record FunctionTypeInstanceCreateDTO(
        int functionTypeId,
        String name,
        Map<String, String> inputValues
) {}
