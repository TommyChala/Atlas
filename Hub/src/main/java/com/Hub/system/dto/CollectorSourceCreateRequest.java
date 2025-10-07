package com.Hub.system.dto;

public record CollectorSourceCreateRequest(
        String systemId,
        String collectorType,
        String EntityType
) {
}
