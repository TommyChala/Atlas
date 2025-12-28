package com.Hub.system.dto;

public record MappingConfigModelCreateDTO(
        String sourceAttribute,
        String targetAttribute,
        String dataType,
        String mappingType,
        MappingExpressionCreateDTO mappingExpression
) {
}
