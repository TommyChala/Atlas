package com.Hub.system.service;

import com.Hub.system.enums.EntityType;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import com.Hub.system.utility.SqlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemService {

    private final SystemRepository systemRepository;
    private final JdbcTemplate jdbcTemplate;

    public SystemService (
            SystemRepository systemRepository,
            JdbcTemplate jdbcTemplate) {
        this.systemRepository = systemRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public SystemModel addNew (SystemModel systemModel) {
        Optional<SystemModel> system = systemRepository.findByName(systemModel.getName());

        if (system.isPresent()) {
            throw new RuntimeException("Error creating system. Name is already taken: " + systemModel.getName());
        }

        String AccountProduction = SqlUtils.getProductionTableName(EntityType.ACCOUNT, systemModel);
        String EntitlementProduction = SqlUtils.getProductionTableName(EntityType.ENTITLEMENT, systemModel);

        if (tableExists(AccountProduction) || tableExists(EntitlementProduction)) {
            throw new IllegalStateException("Database tables already exists for " + systemModel.getName());
        }

        SystemModel newSystem = systemRepository.save(systemModel);
       return newSystem;
    }

    private boolean tableExists(String tableName) {
        String sql = "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, tableName.toLowerCase()));
    }

    private void createProductionTable(String tableName) {
        String sql = String.format("""
            CREATE TABLE %s (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                system_id BIGINT NOT NULL,
                row_hash CHAR(64),
                identity_id UUID, -- For the JoinRules later
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )""", tableName);
        jdbcTemplate.execute(sql);
    }

}
