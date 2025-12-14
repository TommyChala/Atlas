package com.Hub.system.dto;

import java.util.List;

public record MappingConfigCreateBatchDTO (
        String systemId,
        List<MappingConfigModelCreateDTO> data
) {
}
