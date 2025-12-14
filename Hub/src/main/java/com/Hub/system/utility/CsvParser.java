package com.Hub.system.utility;

import com.Hub.account.enums.DataType;
import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountRepository;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CsvParser {

    private final RepositoryMapHelper repositoryMapHelper;
    private final JdbcTemplate jdbcTemplate;
    private final IMappingConfigModelRepository mappingConfigModelRepository;

    public CsvParser(RepositoryMapHelper repositoryMapHelper, JdbcTemplate jdbcTemplate, IMappingConfigModelRepository mappingConfigModelRepository) {
        this.repositoryMapHelper = repositoryMapHelper;
        this.jdbcTemplate = jdbcTemplate;
        this.mappingConfigModelRepository = mappingConfigModelRepository;
    }

    public void parseAccounts(File file, String systemId) throws IOException {

        try (Reader reader = new FileReader(file);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] line;
            String[] headers = csvReader.readNext();

            String[] cleanedHeaders = CSVFileValidator.getCleanedUpHeaders(headers);

            if (!CSVFileValidator.validateHeaderFormat(cleanedHeaders)) {
                throw new IllegalArgumentException("Mandatory headers missing or " +
                        "incorrect separator used.");
            }
            SystemModel system = repositoryMapHelper.getSystemRepository().findBySystemId(Long.valueOf(systemId))
                    .orElseThrow(() -> new RuntimeException("Cannot find system by id")
                    );
            List<MappingConfigModel> mappingConfigModel = mappingConfigModelRepository.findBySystemOrSystemIsNull(system);


            if (!CSVFileValidator.validateHeadersAgainstMapping(cleanedHeaders, mappingConfigModel)) {
                throw new IllegalArgumentException("One or more headers do not match " +
                        "valid source attributes in the mapping config");
            }


            IAccountRepository accountRepository = repositoryMapHelper.getAccountRepository();
            List<AccountAttributeModel> attributes = repositoryMapHelper.getAccountAttributeRepository().findBySystemOrSystemIsNull(system);
            String accountAttributesTable = "Account_Attribute_Staging";

            initializeStagingTables(attributes, mappingConfigModel, accountAttributesTable);

            processRows(csvReader, cleanedHeaders, accountRepository, accountAttributesTable, attributes, mappingConfigModel, system);


        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        //This should be used to fetch only accounts from the relevant system.

    }

    private void processRows(CSVReader csvReader, String[] cleanedHeaders, IAccountRepository accountRepository, String tableName, List<AccountAttributeModel> attributes, List<MappingConfigModel> mappings, SystemModel system) throws IOException, CsvValidationException {
        int BATCH_SIZE = 500;
        List<String[]> batch = new ArrayList<>(BATCH_SIZE);
        String[] line;

        Map<UUID, AccountAttributeModel> attrById =
                attributes.stream()
                        .filter(attr -> attr.getSystem() == null || attr.getSystem().equals(system))
                                .collect(Collectors.toMap(AccountAttributeModel::getId, Function.identity()));

        Map<String, DataType> headerTypes =
                mappings.stream()
                        .filter(m -> attrById.containsKey(m.getTargetAttribute().getId()))
                        .collect(Collectors.toMap(
                                MappingConfigModel::getSourceAttribute,        // key = source column name
                                m -> attrById.get(m.getTargetAttribute().getId()).getDataType()
                        ));

        Map<String, String> sourceToTargetColumn =
                mappings.stream()
                        .collect(Collectors.toMap(
                                m -> m.getSourceAttribute().toLowerCase(),
                                m -> safeColumnName(m.getTargetAttribute().getName())
                        ));

        List<String> insertColumns = Arrays.stream(cleanedHeaders)
                .map(String::trim)
                .map(String::toLowerCase)
                .map(sourceToTargetColumn::get)
                .toList();


        /*
      Map<String, DataType> headerTypes = attributes.stream()
                .collect(Collectors.toMap(AccountAttributeModel::getName, AccountAttributeModel::getDataType));
      */

        while ((line = csvReader.readNext()) != null) {
            batch.add(line);

            if (batch.size() == BATCH_SIZE) {
                persistBatch(batch, cleanedHeaders, accountRepository, tableName, headerTypes, insertColumns);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            persistBatch(batch, cleanedHeaders, accountRepository, tableName, headerTypes, insertColumns);
        }
    }

    private void persistBatch(List<String[]> batch, String[] headers, IAccountRepository accountRepository, String tableName, Map<String, DataType> headerTypes, List<String> insertColumns) {

        String columns = String.join(", ", insertColumns);
        String placeholders = String.join(", ", java.util.Collections.nCopies(headers.length, "?"));

        final String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, columns, placeholders
        );

        int[] updateCounts = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            // 1. setValues: Called for every single row (index i) in the batch
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String[] row = batch.get(i);

                // Set the values for the current row (ps parameters start at index 1)
                for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                    String header = headers[columnIndex];
                    String rawValue = row[columnIndex];
                    DataType type = headerTypes.get(header);

                    if (rawValue == null || rawValue.isBlank()) {
                        ps.setNull(columnIndex + 1, sqlType(type));
                        continue;
                    }
                    switch (type) {
                        case STRING:
                            ps.setString(columnIndex + 1, rawValue);
                            break;
                        case INTEGER:
                            ps.setInt(columnIndex + 1, Integer.parseInt(rawValue));
                            break;
                        case BOOLEAN:
                            ps.setBoolean(columnIndex + 1, Boolean.parseBoolean(rawValue));
                            break;
                        case DATE:
                            // assuming ISO format e.g. "2025-12-09T20:00:00"
                            ps.setTimestamp(columnIndex + 1, java.sql.Timestamp.valueOf(rawValue));
                            break;
                    }

                }
            }

            @Override
            public int getBatchSize() {
                return batch.size();
            }
        });
    }

    private int sqlType(DataType type) {
        return switch (type) {
            case STRING -> java.sql.Types.VARCHAR;
            case INTEGER -> java.sql.Types.INTEGER;
            case BOOLEAN -> java.sql.Types.BOOLEAN;
            case DATE -> java.sql.Types.TIMESTAMP;
        };
    }


    private void initializeStagingTables(List<AccountAttributeModel> accountAttributes, List<MappingConfigModel> mappingConfigModels, String tableName) {

        jdbcTemplate.execute("DROP TABLE IF EXISTS " + tableName);
        jdbcTemplate.execute(buildSQLAccountAttributeStagingTable(tableName, accountAttributes, mappingConfigModels));

    }

    private String buildSQLAccountAttributeStagingTable (
            String tableName,
            List<AccountAttributeModel> accountAttributes,
            List<MappingConfigModel> mappings
    ) {
        Map<UUID, AccountAttributeModel> attributeLookup =
                accountAttributes.stream()
                        .collect(Collectors.toMap(
                                AccountAttributeModel::getId,
                                Function.identity()
                        ));
        Set<String> usedAttributes = new LinkedHashSet<>();

        StringBuilder sql = new StringBuilder("""
        CREATE TABLE IF NOT EXISTS %s (
            staging_id SERIAL PRIMARY KEY
    """.formatted(tableName));

        for (MappingConfigModel mapping : mappings) {
            AccountAttributeModel attr =
                    attributeLookup.get(mapping.getTargetAttribute().getId());

            if (attr == null) {
                throw new IllegalStateException(
                        "Mapping references unknown attribute: "
                                + mapping.getTargetAttribute()
                );
            }
            if (!usedAttributes.add(attr.getName())) {
                continue; // already added
            }

            sql.append(", ")
                    .append(safeColumnName(attr.getName()))
                    .append(" ")
                    .append(toSqlType(attr.getDataType()));
        }
        sql.append("\n);");

        return sql.toString();
    }

    private String safeColumnName(String name) {
        if (!name.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            throw new IllegalArgumentException("Invalid column name: " + name);
        }
        return name.toLowerCase();
    }

    private String toSqlType(DataType type) {
        return switch (type) {
            case DATE    -> "TIMESTAMP";
            case STRING  -> "VARCHAR(100)";
            case INTEGER -> "INT";
            case BOOLEAN -> "BOOLEAN";
        };
    }


}