package com.Hub.organization.dto;

import java.time.LocalDate;
import java.util.Map;

public record JobCreateDTO(
        String externalId,
        String name,
        String level,
        LocalDate validTo,
        Map<String, String> additionalFields
) {}
