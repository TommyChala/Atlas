package com.Hub.system.controller;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.ImportJobModel;
import com.Hub.system.repository.IImportJobModelRepository;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.service.CollectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping
public class CollectorController {

    private final CollectorService collectorService;
    private final IMappingConfigModelRepository mappingConfigModelRepository;
    private final IImportJobModelRepository importJobModelRepository;

    public CollectorController(CollectorService collectorService, IMappingConfigModelRepository mappingConfigModelRepository, IImportJobModelRepository importJobModelRepository) {
        this.collectorService = collectorService;
        this.mappingConfigModelRepository = mappingConfigModelRepository;
        this.importJobModelRepository = importJobModelRepository;
    }

    @PostMapping("/upload/csv")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("systemId") String systemId,
           // @RequestParam("collectorType") String collectorTypeStr,
            @RequestParam("entityType") String entityTypeStr
    ) throws IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        file.transferTo(tempFile);

        CollectorSourceCreateRequest createRequest = new CollectorSourceCreateRequest(
                systemId, CollectorType.valueOf("CSV"),
                EntityType.valueOf(entityTypeStr.toUpperCase()), tempFile
        );
        ImportJobModel importJob = new ImportJobModel();
        importJob.setJobStatus("Pending");
        importJob.setSystemId(systemId);
        ImportJobModel savedJob = importJobModelRepository.save(importJob);
        collectorService.start(createRequest, savedJob.getJobId());
        return ResponseEntity.accepted()
                .body("Import started. Job ID: " + savedJob.getJobId());
    }
}

