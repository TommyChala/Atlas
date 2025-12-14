package com.Hub.system.utility;

import com.Hub.system.enums.CollectorType;
import com.Hub.system.strategy.CollectorStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CollectorRegistry {

    private final Map<CollectorType, CollectorStrategy> collectors = new HashMap<>();

    public CollectorRegistry(List<CollectorStrategy> strategies) {
        for (CollectorStrategy strategy : strategies) {
            collectors.put(strategy.getType(), strategy);
        }
    }

    public CollectorStrategy getStrategy(CollectorType type) {
        return collectors.get(type);
    }
}
