package com.Hub.organization.service;

import com.Hub.organization.dto.JobCreateDTO;
import com.Hub.organization.mapper.JobMapper;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.model.JobModel;
import com.Hub.organization.repository.FunctionTypeRepository;
import com.Hub.organization.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final FunctionTypeRepository functionTypeRepository;
    private final FunctionTypeInstanceService functionTypeInstanceService;

    public JobService (JobRepository jobRepository, JobMapper jobMapper,
                       FunctionTypeRepository functionTypeRepository, FunctionTypeInstanceService functionTypeInstanceService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.functionTypeRepository = functionTypeRepository;
        this.functionTypeInstanceService = functionTypeInstanceService;
    }

    public JobModel findById(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found for Id: " + id));
    }

    public ResponseEntity<JobModel> addNew(JobCreateDTO jobCreateDTO) {

        JobModel newJob = jobMapper.toModel(jobCreateDTO);
        newJob.setWhenCreated(LocalDate.now());
        if (newJob.getValidTo() == null) {
            newJob.setValidTo(Date.valueOf(LocalDate.of(9999, 12, 31)).toLocalDate());
        }
        try {
            jobRepository.save(newJob);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FunctionTypeModel functionType = functionTypeRepository.findByName("Job")
                .orElseThrow(() -> new EntityNotFoundException("FunctionType 'Job' not found"));

        Map<String, String> additionalFields;
        if (jobCreateDTO.additionalFields() != null) {
            additionalFields = new HashMap<>(jobCreateDTO.additionalFields());
        } else {
            additionalFields = new HashMap<>();
        }
        if (jobCreateDTO.externalId() != null) {
            additionalFields.put("externalId", jobCreateDTO.externalId());
        }

        functionTypeInstanceService.createFunctionTypeInstance(functionType.getId(), newJob.getName(), additionalFields);

        return ResponseEntity.ok(newJob);

    }

}
