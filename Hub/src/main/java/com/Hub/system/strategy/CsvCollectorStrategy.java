package com.Hub.system.strategy;

import com.Hub.system.dto.CollectorRawCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import com.Hub.system.service.FileStorageService;
import com.Hub.system.processor.EntityProcessor;
import com.Hub.system.factory.EntityProcessorFactory;
import com.Hub.system.service.ReconciliationService;
import com.Hub.system.utility.SqlUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class CsvCollectorStrategy implements CollectorStrategy<File> {

    private final EntityProcessorFactory processorFactory;
    private final FileStorageService fileStorageService;
    private final SystemRepository systemRepository;
    private final ReconciliationService reconciliationService;

    public CsvCollectorStrategy (
            EntityProcessorFactory entityProcessorFactory,
            FileStorageService fileStorageService,
            SystemRepository systemRepository,
            ReconciliationService reconciliationService) {
        this.processorFactory = entityProcessorFactory;
        this.fileStorageService = fileStorageService;
        this.systemRepository = systemRepository;
        this.reconciliationService = reconciliationService;

    }
    @Override
    public CollectorType getType() {
        return CollectorType.CSV;
    }

    @Override
    public void collect(CollectorRawCreateRequest source) {

        File file = null;

        try {
            file = fileStorageService.retrieveFile(source.filePath());

            //Long systemId = Long.valueOf(source.systemId());
            SystemModel system = systemRepository.findBySystemId(Long.valueOf(source.systemId()))
                    .orElseThrow(() -> new RuntimeException("Cannot find system with id: "+source.systemId())
                    );

            EntityProcessor<?> processor = processorFactory.getProcessor(source.entityType());

            processor.process(file, system);

            reconciliationService.performReconciliation(
                    SqlUtils.getStagingTableName(source.entityType(), system),
                    system.getSystemId(), "businessKey"
            );
        }
        catch (IOException e) {
            throw new RuntimeException("Error during CSV processing file I/O for file: " + source.filePath(), e);
        } catch (Exception e) {
            // Handle other runtime exceptions from processing
            throw new RuntimeException("CSV Collection failed for entity " + source.entityType() + ".", e);
        } finally {
            // 5. Cleanup: Always attempt to delete the temporary file after processing
            if (file != null && file.exists()) {
                boolean deleted = fileStorageService.deleteFile(file.getAbsolutePath());
                if (!deleted) {
                    // Log this warning instead of throwing an exception
                    System.err.println("Warning: Failed to delete temporary file: " + file.getAbsolutePath());
                }
            }
        }
    }
}
