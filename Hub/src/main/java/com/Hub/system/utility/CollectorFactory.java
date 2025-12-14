package com.Hub.system.utility;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.strategy.CollectorStrategy;
import org.springframework.stereotype.Component;

@Component
public class CollectorFactory {
    private final CollectorRegistry collectorRegistry;

    public CollectorFactory (CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    public CollectorStrategy getStrategy (CollectorSourceCreateRequest request) {

        return collectorRegistry.getStrategy(request.collectorType());
    }
}
