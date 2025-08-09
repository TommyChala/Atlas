package com.Hub.organization.service;

import com.Hub.organization.dto.OrganizationalUnitCreateDTO;
import com.Hub.organization.mapper.OrganizationalUnitMapper;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.model.FunctionTypeInstanceModel;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.model.OrganizationalUnitModel;
import com.Hub.organization.repository.FunctionTypeAttributeRepository;
import com.Hub.organization.repository.FunctionTypeInstanceRepository;
import com.Hub.organization.repository.FunctionTypeRepository;
import com.Hub.organization.repository.OrganizationalUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrganizationalUnitService {

    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final FunctionTypeRepository functionTypeRepository;
    private final OrganizationalUnitMapper organizationalUnitMapper;
    private final FunctionTypeInstanceService functionTypeInstanceService;

    public OrganizationalUnitService(OrganizationalUnitRepository organizationalUnitRepository, FunctionTypeRepository functionTypeRepository,
                                     OrganizationalUnitMapper organizationalUnitMapper,
                                     FunctionTypeInstanceService functionTypeInstanceService) {
        this.organizationalUnitRepository = organizationalUnitRepository;
        this.functionTypeRepository = functionTypeRepository;
        this.organizationalUnitMapper = organizationalUnitMapper;
        this.functionTypeInstanceService = functionTypeInstanceService;
    }

    public OrganizationalUnitModel findById(UUID id) {
        return organizationalUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrgUnit not found by ID: " + id));
    }

    public ResponseEntity<OrganizationalUnitModel> addNew(OrganizationalUnitCreateDTO createDTO) {
        OrganizationalUnitModel newOU = organizationalUnitMapper.toModel(createDTO);
        newOU.setWhenCreated(LocalDate.now());
        if (newOU.getValidTo() == null) {
            newOU.setValidTo(LocalDate.of(9999, 12, 31));
        }
            try {
                organizationalUnitRepository.save(newOU);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            FunctionTypeModel functionType = functionTypeRepository.findByName("OrgUnit")
                    .orElseThrow(() -> new EntityNotFoundException("FunctionType 'OrgUnit' not found"));

            Map<String, String> additionalFields;
            if (createDTO.additionalFields() != null) {
                additionalFields = new HashMap<>(createDTO.additionalFields());
            } else {
                additionalFields = new HashMap<>();
            }
            if (createDTO.externalId() != null) {
                additionalFields.put("externalId", createDTO.externalId());
            }

            functionTypeInstanceService.createFunctionTypeInstance(functionType.getId(), newOU.getName(), additionalFields);

            return ResponseEntity.ok(newOU);
        }
}
