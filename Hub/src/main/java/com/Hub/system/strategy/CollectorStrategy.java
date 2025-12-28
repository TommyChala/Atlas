package com.Hub.system.strategy;

import com.Hub.system.dto.CollectorRawCreateRequest;
import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.service.ReconciliationService;

public interface CollectorStrategy<T> {
    CollectorType getType();

    void collect(CollectorRawCreateRequest source);

}
