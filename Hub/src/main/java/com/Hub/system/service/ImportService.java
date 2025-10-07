package com.Hub.system.service;

import org.springframework.stereotype.Service;

@Service
public class ImportService {

    private final CollectorService collectorService;
    private final MappingExpressionEngine mappingExpressionEngine;

    public ImportService (CollectorService collectorService, MappingExpressionEngine mappingExpressionEngine) {
        this.collectorService = collectorService;
        this.mappingExpressionEngine = mappingExpressionEngine;
    }
}
