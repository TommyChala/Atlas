package com.Hub.identity.service;


import com.Hub.identity.dto.IdentityCreateDTO;
import com.Hub.identity.dto.IdentityModelRequest;
import com.Hub.identity.mapper.IdentityMapper;
import com.Hub.identity.model.IdentityModel;
import com.Hub.identity.repository.IdentityRepository;
import com.Hub.kafka.KafkaProducer;
import com.Hub.organization.model.JobModel;
import com.Hub.organization.model.OrganizationalUnitModel;
import com.Hub.organization.repository.JobRepository;
import com.Hub.organization.repository.OrganizationalUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final JobRepository jobRepository;
    private final IdentityMapper identityMapper;
    private final KafkaProducer kafkaProducer;

    public IdentityService (IdentityRepository identityRepository, OrganizationalUnitRepository organizationalUnitRepository, JobRepository jobRepository, IdentityMapper identityMapper, KafkaProducer kafkaProducer) {
        this.identityRepository = identityRepository;
        this.organizationalUnitRepository = organizationalUnitRepository;
        this.jobRepository = jobRepository;
        this.identityMapper = identityMapper;
        this.kafkaProducer = kafkaProducer;

    }

    public IdentityModelRequest findById(UUID id) {
        IdentityModel identity = identityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found for ID: " + id));

        return identityMapper.IdentityToDTO(identity);

    }

    public ResponseEntity<IdentityCreateDTO> addNew(IdentityCreateDTO identityCreateDTO) {
        IdentityModel newIdentity = identityMapper.CreateDTOToIdentity(identityCreateDTO);
        newIdentity.setOrganizationalUnit(organizationalUnitRepository.findByExternalId(identityCreateDTO.getOrganizationalUnitId())
                .orElseThrow(() -> new EntityNotFoundException("OrgUnit not found by ID" + identityCreateDTO.getOrganizationalUnitId()))
        );
        newIdentity.setJob(jobRepository.findByExternalId(identityCreateDTO.getJobId())
                        .orElseThrow(() -> new EntityNotFoundException("Job not found by ID" + identityCreateDTO.getJobId()))
                        );
        identityRepository.save(newIdentity);

        kafkaProducer.sendEventCreate(newIdentity);

        return ResponseEntity.ok(identityCreateDTO);
    }
}
