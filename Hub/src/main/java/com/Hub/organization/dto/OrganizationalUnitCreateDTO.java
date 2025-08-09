package com.Hub.organization.dto;

import java.time.LocalDate;
import java.util.Map;

public record OrganizationalUnitCreateDTO (
     String externalId,
     String name,
     LocalDate validTo,
     Map<String, String> additionalFields
) {}
