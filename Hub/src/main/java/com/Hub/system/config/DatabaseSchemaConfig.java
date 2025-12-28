package com.Hub.system.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseSchemaConfig {
@Bean
public ApplicationRunner initializeSchema(JdbcTemplate jdbcTemplate) {
    return args -> {
        // This creates the shared 'Memory' table for all systems
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS account_production_hashes (
                    business_key VARCHAR(255) NOT NULL,
                    system_id BIGINT NOT NULL,
                    row_hash CHAR(64) NOT NULL,
                    PRIMARY KEY (business_key, system_id)
                )
            """);

        // Optional: Add an index for even faster reconciliation lookups
        jdbcTemplate.execute("""
                CREATE INDEX IF NOT EXISTS idx_hash_lookup
                ON account_production_hashes (system_id, business_key)
            """);
        };
    }
}
