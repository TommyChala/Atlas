package com.Hub.account.processor;

import com.Hub.account.enums.DataType;
import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.processor.EntityProcessor;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.Hub.system.service.GenericStagingService;
import com.Hub.system.service.MappingExpressionEngine;
import com.Hub.system.service.ReconciliationService;
import com.Hub.system.utility.*;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AccountProcessor implements EntityProcessor<CollectedEntity> {

    private final RepositoryMapHelper repositoryMapHelper;
    private final IMappingConfigModelRepository mappingConfigModelRepository;
    private final CsvDataReader csvDataReader;
    private final GenericStagingService stagingService;
    private final SqlUtils sqlUtils;
    private final ReconciliationService reconciliationService;
    private final MappingExpressionEngine mappingExpressionEngine;

    // Renamed record to avoid conflict with Kafka's ProcessingContext
    private record AccountProcessingContext(
            List<MappingConfigModel> activeMappings,
            Map<String, DataType> targetDataTypes,
            List<String> insertColumns
    ) {}

    public AccountProcessor(
            RepositoryMapHelper repositoryMapHelper,
            IMappingConfigModelRepository mappingConfigModelRepository,
            CsvDataReader csvDataReader,
            GenericStagingService stagingService,
            SqlUtils sqlUtils,
            ReconciliationService reconciliationService,
            MappingExpressionEngine mappingExpressionEngine
    ) {
        this.repositoryMapHelper = repositoryMapHelper;
        this.mappingConfigModelRepository = mappingConfigModelRepository;
        this.csvDataReader = csvDataReader;
        this.stagingService = stagingService;
        this.sqlUtils = sqlUtils;
        this.reconciliationService = reconciliationService;
        this.mappingExpressionEngine = mappingExpressionEngine;
    }

    @Override
    public EntityType getType() {
        return EntityType.ACCOUNT;
    }

    @Override
    @Transactional
    public void process(File file, SystemModel system) throws IOException {
        try {
            System.out.println(">>> [PROCESSOR START] System: " + system.getName());

            // 1. Header Extraction
            String[] rawCsvHeaders = csvDataReader.readHeaders(file);
            System.out.println("DEBUG: CSV Headers read successfully: " + Arrays.toString(rawCsvHeaders));

            // 2. Mapping Fetch (The current breaking point)
            List<MappingConfigModel> mappings;
            try {
                mappings = mappingConfigModelRepository.findBySystemOrSystemIsNull(system);
                System.out.println("DEBUG: Mappings fetched. Count: " + (mappings != null ? mappings.size() : "NULL"));
            } catch (Throwable t) {
                System.err.println("!!! FATAL: Hibernate Hydration Failed during Mapping Fetch !!!");
                t.printStackTrace();
                throw new RuntimeException("DB Fetch Error", t);
            }

            // 3. Attribute Fetch
            List<AccountAttributeModel> attributes;
            try {
                attributes = repositoryMapHelper.getAccountAttributeRepository().findBySystemOrSystemIsNull(system);
                System.out.println("DEBUG: Attributes fetched. Count: " + (attributes != null ? attributes.size() : "NULL"));
            } catch (Throwable t) {
                System.err.println("!!! FATAL: Attribute Fetch Failed !!!");
                t.printStackTrace();
                throw new RuntimeException("Attribute Fetch Error", t);
            }

            // 4. Validation
            System.out.println("Step1: Validating CSV vs Mappings");
            if (!CSVFileValidator.validateHeadersAgainstMapping(rawCsvHeaders, mappings)) {
                System.err.println("❌ Validation failed in CSVFileValidator");
                throw new IllegalArgumentException("CSV missing required columns for DIRECT mappings.");
            }

            // 5. Execution
            System.out.println("Step2: Setting up Staging Table");
            Set<String> usedAttributes = getListOfUsedAttributes(mappings, attributes);
            final String STAGING_TABLE = SqlUtils.getStagingTableName(EntityType.ACCOUNT, system);
            setupStagingTable(STAGING_TABLE, usedAttributes);

            System.out.println("Step3: Building Context");
            AccountProcessingContext context = buildProcessingContext(system, mappings, attributes);

            System.out.println("Step4: Starting Stream and Persist");
            streamAndPersist(file, STAGING_TABLE, rawCsvHeaders, context);
            sqlUtils.generateAttributeHashes(STAGING_TABLE, usedAttributes);
            reconciliationService.performReconciliation(STAGING_TABLE, system.getSystemId(), "businesskey");
            reconciliationService.promoteNewAccounts(STAGING_TABLE, system, "businesskey");
            reconciliationService.promoteAttributes(STAGING_TABLE, system, attributes);
            reconciliationService.updateProductionHashes(STAGING_TABLE, system, "businesskey");

            System.out.println(">>> [PROCESSOR SUCCESS] System: " + system.getName());

        } catch (Throwable e) {
            // Catching Throwable ensures we see everything, including Errors
            System.err.println("****************************************");
            System.err.println("CRITICAL FAILURE in AccountProcessor");
            System.err.println("Exception Type: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("****************************************");
            throw new RuntimeException("Processor failed: " + e.getMessage(), e);
        }
    }

    private void streamAndPersist(File file, String tableName, String[] headers, AccountProcessingContext ctx)
            throws IOException, CsvValidationException {

        int BATCH_SIZE = 500;
        final List<Map<String, String>> transformedBatch = new ArrayList<>(BATCH_SIZE);

        System.out.println("DEBUG: Entering streamAndPersist. File size: " + file.length());

        try {
            csvDataReader.streamCsv(file, headers, csvRow -> {
                try {
                    // If this prints, we know the loop is working
                    if (transformedBatch.isEmpty()) {
                        System.out.println("DEBUG: Processing very first row...");
                    }

                    Map<String, String> transformedRow = new HashMap<>();

                    for (MappingConfigModel mapping : ctx.activeMappings()) {
                        String targetColName = SqlUtils.safeColumnName(mapping.getTargetAttribute().getName());
                        Object calculatedValue = mappingExpressionEngine.calculateValue(mapping, csvRow);
                        transformedRow.put(targetColName, calculatedValue != null ? calculatedValue.toString() : null);
                    }

                    transformedBatch.add(transformedRow);
                    if (transformedBatch.size() >= BATCH_SIZE) {
                        flush(tableName, transformedBatch, ctx);
                    }
                } catch (Exception rowEx) {
                    System.err.println("ERROR inside CSV row processing: " + rowEx.getMessage());
                    rowEx.printStackTrace();
                    throw rowEx; // Re-throw to stop the silent failure
                }
            });
        } catch (Exception streamEx) {
            System.err.println("ERROR during CSV streaming: " + streamEx.getMessage());
            streamEx.printStackTrace();
            throw streamEx;
        }

        System.out.println("DEBUG: Finished streamCsv loop. Batch size remaining: " + transformedBatch.size());

        if (!transformedBatch.isEmpty()) {
            flush(tableName, transformedBatch, ctx);
            System.out.println("DEBUG: Final flush complete.");
        }
    }

    private void flush(String table, List<Map<String, String>> batch, AccountProcessingContext ctx) {
        // We pass NULL for sourceToTarget because the batch is already transformed
        stagingService.persistBatch(
                table,
                batch,
                ctx.insertColumns().toArray(new String[0]),
                ctx.targetDataTypes(),
                ctx.insertColumns(),
                null
        );
        batch.clear();
    }

    private void setupStagingTable(String tableName, Set<String> usedAttributes) {
        stagingService.executeDDL("DROP TABLE IF EXISTS " + tableName);
        StringBuilder sql = new StringBuilder("""
                    CREATE TABLE %s (
                        staging_id SERIAL PRIMARY KEY,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        import_status INT DEFAULT 0,
                        row_hash char(64)
                """.formatted(tableName));

        for (String attr : usedAttributes) {
            sql.append(", ").append(SqlUtils.safeColumnName(attr)).append(" VARCHAR(255)");
        }
        sql.append("\n);");
        stagingService.executeDDL(sql.toString());
    }

    private AccountProcessingContext buildProcessingContext(SystemModel system, List<MappingConfigModel> mappings, List<AccountAttributeModel> attributes) {
        // 1. Map attributes by ID safely
        Map<UUID, AccountAttributeModel> attrById = attributes.stream()
                .collect(Collectors.toMap(AccountAttributeModel::getId, Function.identity(), (existing, replacement) -> existing));

        // 2. Build our maps using a for-loop for better debugging and safety
        Map<String, DataType> targetDataTypes = new HashMap<>();
        List<String> insertColumns = new ArrayList<>();

        for (MappingConfigModel m : mappings) {
            AccountAttributeModel targetAttr = attrById.get(m.getTargetAttribute().getId());

            if (targetAttr == null) {
                // Log it or skip it, but don't let it crash the whole process
                System.err.println("CRITICAL: Mapping exists for attribute ID " + m.getTargetAttribute().getId() + " but attribute definition is missing.");
                continue;
            }

            String safeName = SqlUtils.safeColumnName(targetAttr.getName());

            // Add to columns list
            insertColumns.add(safeName);

            // Add to data types (if duplicate, the last one wins)
            targetDataTypes.put(safeName, targetAttr.getDataType());
        }

        return new AccountProcessingContext(mappings, targetDataTypes, insertColumns);
    }

    private static Set<String> getListOfUsedAttributes(List<MappingConfigModel> mappings, List<AccountAttributeModel> accountAttributes) {
        Set<String> usedAttributes = new LinkedHashSet<>();
        Map<UUID, AccountAttributeModel> attributeLookup = accountAttributes.stream()
                .collect(Collectors.toMap(AccountAttributeModel::getId, Function.identity()));

        for (MappingConfigModel mapping : mappings) {
            AccountAttributeModel attr = attributeLookup.get(mapping.getTargetAttribute().getId());
            if (attr != null) {
                usedAttributes.add(attr.getName());
            }
        }
        return usedAttributes;
    }
}