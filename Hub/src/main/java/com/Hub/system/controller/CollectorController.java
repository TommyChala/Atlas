package com.Hub.system.controller;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.CollectorSource;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.service.CollectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class CollectorController {

    private final CollectorService collectorService;
    private final IMappingConfigModelRepository mappingConfigModelRepository;

    public CollectorController(CollectorService collectorService, IMappingConfigModelRepository mappingConfigModelRepository) {
        this.collectorService = collectorService;
        this.mappingConfigModelRepository = mappingConfigModelRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("systemId") String systemId,
            @RequestParam("collectorType") String collectorTypeStr,
            @RequestParam("entityType") String entityTypeStr
    ) {
        Path tempFile = null;
        try {
            CollectorType collectorType = CollectorType.valueOf(collectorTypeStr.toUpperCase());
            EntityType entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
            tempFile = Files.createTempFile("upload", ".csv");
            file.transferTo(tempFile);

            List<MappingConfigModel> mappings = mappingConfigModelRepository.findAll();

            CollectorSource source = new CollectorSource();
            source.setFile(tempFile);
            source.setSystemId(Long.parseLong(systemId));
            source.setCollectorType(collectorType);
            source.setEntityType(entityType);
            System.out.println("CollectorType from request: " + source.getCollectorType());
            collectorService.run(source, mappings);
            return ResponseEntity.ok("CSV processed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing CSV");
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (Exception ignored) {
                }
            }
        }

    }
}

