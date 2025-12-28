package com.Hub.system.dto;

import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;

import java.io.File;

public record CollectorRawCreateRequest(
        String systemId,
        CollectorType collectorType,
        EntityType entityType,
        String filePath
) {
}
