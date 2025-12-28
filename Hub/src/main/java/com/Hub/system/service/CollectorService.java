package com.Hub.system.service;

import com.Hub.system.dto.CollectorRawCreateRequest;
import com.Hub.system.repository.IImportJobModelRepository;
import com.Hub.system.utility.CollectorFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CollectorService {

    private final CollectorFactory collectorFactory;
    private final IImportJobModelRepository importJobModelRepository;

    public CollectorService(
            CollectorFactory collectorFactory,
            IImportJobModelRepository importJobModelRepository) {
        this.collectorFactory = collectorFactory;
        this.importJobModelRepository = importJobModelRepository;
    }

    public CompletableFuture<Void> start(CollectorRawCreateRequest request, Long jobId) {
        return CompletableFuture.runAsync(() -> {
            try {
                collectorFactory.getStrategy(request).collect(request);
                updateJobStatus(jobId, "In Progress", "Staging done");
                // return CompletableFuture.completedFuture(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateJobStatus (Long jobId, String status, String message) {
        importJobModelRepository.findById(jobId).ifPresent(job -> {
            job.setJobStatus(status);
            importJobModelRepository.save(job);
        });
    }

}
    //Create service that that starts by extracting data from csv file -> csvExtractor.
    //Then we need to send all the data through the MappingExpressionEngine
    //Then we need to match rows to an existing account and compare to-be with as-is.
    //Then we need to build our JoinRules so that we can join accounts to identities.
    //Keep in mind that this service should be usable for entitlements as well (if possible)
