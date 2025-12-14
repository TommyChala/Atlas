package com.Hub.system.service;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.strategy.CollectorStrategy;
import com.Hub.system.utility.CollectorFactory;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CollectorService {

    private final CollectorFactory collectorFactory;

    public CollectorService(CollectorFactory collectorFactory) {
        this.collectorFactory = collectorFactory;
    }

    public CompletableFuture<Void> start(CollectorSourceCreateRequest request, Long jobId) {

        collectorFactory.getStrategy(request).collect(request);
        return CompletableFuture.completedFuture(null);
    }

}
    //Create service that that starts by extracting data from csv file -> csvExtractor.
    //Then we need to send all the data through the MappingExpressionEngine
    //Then we need to match rows to an existing account and compare to-be with as-is.
    //Then we need to build our JoinRules so that we can join accounts to identities.
    //Keep in mind that this service should be usable for entitlements as well (if possible)
