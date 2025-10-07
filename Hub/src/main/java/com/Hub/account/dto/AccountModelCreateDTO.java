package com.Hub.account.dto;

public record AccountModelCreateDTO(
        String accountId,
        String accountName,
        Long systemId
) {
}
