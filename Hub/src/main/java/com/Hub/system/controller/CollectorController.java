package com.Hub.system.controller;

import com.Hub.system.dto.CollectorRawCreateRequest;
import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.ImportJobModel;
import com.Hub.system.repository.IImportJobModelRepository;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.service.CollectorService;
import com.Hub.system.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping
public class CollectorController {

    private final CollectorService collectorService;
    private final FileStorageService fileStorageService;
    private final IImportJobModelRepository importJobModelRepository;

    public CollectorController(CollectorService collectorService, FileStorageService fileStorageService, IImportJobModelRepository importJobModelRepository) {
        this.collectorService = collectorService;
        this.fileStorageService = fileStorageService;
        this.importJobModelRepository = importJobModelRepository;
    }

    @PostMapping("/upload/csv")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("systemId") String systemId,
           // @RequestParam("collectorType") String collectorTypeStr,
            @RequestParam("entityType") String entityTypeStr
    ) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File must not be empty");
        }

        String stableFilePath = fileStorageService.storeFile(file);

        CollectorRawCreateRequest createRequest = new CollectorRawCreateRequest(
                systemId, CollectorType.valueOf("CSV"),
                EntityType.valueOf(entityTypeStr.toUpperCase()), stableFilePath
        );
        ImportJobModel importJob = new ImportJobModel();
        importJob.setJobStatus("Pending");
        importJob.setSystemId(systemId);
        importJob.setStartTime(LocalDateTime.now());
        ImportJobModel savedJob = importJobModelRepository.save(importJob);
        collectorService.start(createRequest, savedJob.getJobId());
        return ResponseEntity.accepted()
                .body("Import started. Job ID: " + savedJob.getJobId());
    }
}

