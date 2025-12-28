package com.Hub.system.dto;

import com.Hub.system.utility.CollectedEntity;

import java.util.Map;

public record CollectedAccount(
        String systemId,
        String accountIdentifier,
        Map<String, String> rawAttributes
) implements CollectedEntity {
}
