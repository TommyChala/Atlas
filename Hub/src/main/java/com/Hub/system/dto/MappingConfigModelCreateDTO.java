package com.Hub.system.dto;

public record MappingConfigModelCreateDTO(
        String sourceAttribute,
        String targetAttribute,
        String systemId,
        String dataType
) {
}
