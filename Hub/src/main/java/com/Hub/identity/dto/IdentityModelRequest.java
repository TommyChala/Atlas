package com.Hub.identity.dto;

import java.time.LocalDate;

public record IdentityModelRequest(
        String identityId,
        String firstName,
        String lastName,
        String email,
        String organizationalUnitName,
        String jobId,
        String managerId,
        String identityStatus,
        LocalDate validFrom,
        LocalDate validTo
) {}
