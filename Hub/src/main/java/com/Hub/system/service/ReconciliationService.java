package com.Hub.system.service;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.SystemModel;
import com.Hub.system.utility.SqlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReconciliationService {

    private final JdbcTemplate jdbcTemplate;

    public ReconciliationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void performReconciliation(String stagingTable, Long systemId, String businessKeyCol) {


        // 1. Identify UPDATES
        // Business Key exists for this system, but data (hash) has changed.
        String updateSql = String.format("""
            UPDATE %s s
            SET import_status = 3
            FROM account_production_hashes p
            WHERE s.%s = p.business_key
              AND p.system_id = ?
              AND s.row_hash != p.row_hash
            """, stagingTable, businessKeyCol);


        // 2. Identify INSERTS
        // Business Key does not exist at all for this system in our memory.
        String insertSql = String.format("""
    UPDATE %s s
    SET import_status = 1 -- Let's use 1 for 'NEW/INSERT'
    WHERE NOT EXISTS (
        SELECT 1 FROM account_production_hashes p 
        WHERE LOWER(TRIM(p.business_key)) = LOWER(TRIM(s.%s))
          AND p.system_id = ?
    )
    """, stagingTable, businessKeyCol);

        // 3. Identify UNCHANGED (Optional but helpful for debugging)
        String unchangedSql = String.format("""
            UPDATE %s s
            SET import_status = 2
            FROM account_production_hashes p
            WHERE s.%s = p.business_key
              AND p.system_id = ?
              AND s.row_hash = p.row_hash
            """, stagingTable, businessKeyCol);


        jdbcTemplate.update(updateSql, systemId);
        jdbcTemplate.update(insertSql, systemId);
        jdbcTemplate.update(unchangedSql, systemId);
    }

    public void promoteNewAccounts(String stagingTable, SystemModel system, String businessKeyCol) {
        String sql = String.format("""
        INSERT INTO account (uid, account_id, system_id)
        SELECT gen_random_uuid(), s.%s, ?
        FROM %s s
        WHERE s.import_status = 1 -- Only the 'New' ones
        ON CONFLICT (account_id) DO NOTHING
        """, businessKeyCol, stagingTable);

        int count = jdbcTemplate.update(sql, system.getId());
        System.out.println("Promoted " + count + " new accounts to the production account table.");
    }

    public void promoteAttributes(String stagingTable, SystemModel system, List<AccountAttributeModel> attributes) {
        for (AccountAttributeModel attr : attributes) {

            // 1. Determine which column to target based on the attribute's type
            String targetColumn = "value_string"; // default
            String castType = "TEXT";

            // Assuming your AccountAttributeModel has an enum or string for type
            switch (attr.getDataType()) {
                case INTEGER -> {
                    targetColumn = "value_integer";
                    castType = "INTEGER";
                }
                case DATE -> {
                    targetColumn = "value_datetime";
                    castType = "TIMESTAMP";
                }
                case FLOAT -> {
                    targetColumn = "value_float";
                    castType = "DOUBLE PRECISION";
                }
                case BOOLEAN -> {
                    targetColumn = "value_boolean";
                    castType = "BOOLEAN";
                }
            }

            // 2. Build the SQL with the dynamic column and casting
            String sql = String.format("""
            INSERT INTO account_attribute_value_model (id, account_id, attribute_id, %s, is_row_latest)
            SELECT gen_random_uuid(), a.uid, ?, CAST(s.%s AS %s), true
            FROM %s s
            JOIN account a ON s.businesskey = a.account_id
            WHERE s.import_status IN (1, 2) 
              AND a.system_id = ?
            ON CONFLICT (account_id, attribute_id) 
            DO UPDATE SET %s = EXCLUDED.%s, is_row_latest = true
            """,
                    targetColumn,
                    SqlUtils.safeColumnName(attr.getName()), castType,
                    stagingTable,
                    targetColumn, targetColumn
            );

            int count = jdbcTemplate.update(sql, attr.getId(), system.getId());
            System.out.println("Mapped attribute [" + attr.getName() + "] to column [" + targetColumn + "] for " + count + " rows.");
        }
    }

    public void updateProductionHashes(String stagingTable, SystemModel system, String businessKeyCol) {
        // We use an 'UPSERT' (INSERT ... ON CONFLICT)
        // This ensures that new accounts are added, and existing ones get their hash updated.
        String sql = String.format("""
        INSERT INTO account_production_hashes (business_key, system_id, row_hash)
        SELECT s.%s, ?, s.row_hash
        FROM %s s
        WHERE s.import_status IN (1, 2) -- Only sync rows we actually processed (New or Updated)
        ON CONFLICT (business_key, system_id) 
        DO UPDATE SET row_hash = EXCLUDED.row_hash
        """, businessKeyCol, stagingTable);

        int count = jdbcTemplate.update(sql, system.getSystemId());
        System.out.println("Memory Updated: Synced " + count + " hashes for system ID: " + system.getId());
    }
}

