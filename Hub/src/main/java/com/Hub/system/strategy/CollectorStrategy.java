package com.Hub.system.strategy;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;

public interface CollectorStrategy<T> {
    CollectorType getType();

    void collect(CollectorSourceCreateRequest source);
}
