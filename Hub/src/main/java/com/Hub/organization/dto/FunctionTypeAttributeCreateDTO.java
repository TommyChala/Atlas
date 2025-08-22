package com.Hub.organization.dto;

public record FunctionTypeAttributeCreateDTO(
        int functionTypeId,
        boolean isRequired,
        String dataType,
        String name
) {
}
