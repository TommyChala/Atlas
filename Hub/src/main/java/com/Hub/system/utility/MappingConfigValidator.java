package com.Hub.system.utility;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MappingConfigValidator {

    private final IAccountAttributeRepository accountAttributeRepository;
    private final SystemRepository systemRepository;

    public MappingConfigValidator(IAccountAttributeRepository accountAttributeRepository, SystemRepository systemRepository) {
        this.accountAttributeRepository = accountAttributeRepository;
        this.systemRepository = systemRepository;
    }

    public void validateMandatoryAttributes(
            List<MappingConfigModelCreateDTO> mappings,
            String systemId
    ) {
        SystemModel system = systemRepository.findBySystemId(Long.valueOf(systemId))
                .orElseThrow(() -> new RuntimeException("System not found")
                );
        // 1. Get all attributes for this system (or system-agnostic)
        List<AccountAttributeModel> allAttributes = accountAttributeRepository
                .findBySystemOrSystemIsNull(system);

        // 2. Filter only required attributes
        Set<String> mandatoryAttributeNames = allAttributes.stream()
                .filter(AccountAttributeModel::isRequired)
                .map(AccountAttributeModel::getName)
                .collect(Collectors.toSet());

        // 3. Extract all attribute names from the payload
        Set<String> providedAttributeNames = mappings.stream()
                .map(MappingConfigModelCreateDTO::targetAttribute) // or sourceAttribute, depending on your logic
                .collect(Collectors.toSet());

        // 4. Check if any mandatory attribute is missing
        Set<String> missingAttributes = new HashSet<>(mandatoryAttributeNames);
        missingAttributes.removeAll(providedAttributeNames);

        if (!missingAttributes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Missing mandatory attributes: " + missingAttributes
            );
        }
    }
}

