package com.Hub.system.utility;

import com.Hub.system.enums.EntityType;
import com.Hub.system.model.SystemModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SqlUtils {

    private final JdbcTemplate jdbcTemplate;

    // Standard constructor injection
    public SqlUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String safeColumnName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name cannot be null or blank.");
        }

        // This is the regex check: start with a letter/underscore, followed by letters, numbers, or underscores.
        if (!name.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            // NOTE: Many databases allow more characters, but this strict check is generally safer.
            throw new IllegalArgumentException("Invalid column name: " + name +
                    ". Must start with a letter or underscore and contain only letters, numbers, or underscores.");
        }
        return name.toLowerCase();
    }

    public void generateAttributeHashes(String tableName, Set<String> usedAttributes) {
        String concatExpression = usedAttributes.stream()
                .map(attr -> "COALESCE(" + SqlUtils.safeColumnName(attr) + "::text, '')")
                .collect(Collectors.joining(", '|', "));

        String sql = String.format(
                "UPDATE %s SET row_hash = encode(sha256(concat_ws('|', %s)::bytea), 'hex')",
                tableName, concatExpression
        );

        jdbcTemplate.execute(sql);
    }

    public static String getStagingTableName(EntityType entityType, SystemModel system) {

        return system.getName().toUpperCase() + "_" + entityType.toString().toUpperCase() + "_STAGING_TABLE";
    }

    public static String getProductionTableName(EntityType entityType, SystemModel system) {

        return system.getName().toUpperCase() + "_" + entityType.toString().toUpperCase() + "_PRODUCTION_TABLE";
    }

}
