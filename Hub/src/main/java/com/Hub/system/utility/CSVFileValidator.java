package com.Hub.system.utility;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.enums.MappingType;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.MappingExpressionModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CSVFileValidator {


    public CSVFileValidator () {
    }

    public static boolean validateHeaderFormat (String[] headers) {

        return headers != null && headers.length > 1;
    }

    public static String[] getCleanedUpHeaders (String[] headers) {
        return Arrays.stream(headers)
                .map(h -> h.replace("\uFEFF", "").trim())
                .toArray(String[]::new);
    }

    //new
    public static boolean validateHeadersAgainstMapping(String[] headers, List<MappingConfigModel> mappingConfigs) {
        // 1. Log what we received from the CSV
        Set<String> csvHeaders = Arrays.stream(headers)
                .map(h -> {
                    String cleaned = h.replace("\uFEFF", "").trim().toLowerCase();
                    return cleaned;
                })
                .collect(Collectors.toSet());

        System.out.println("--- CSV VALIDATION START ---");
        System.out.println("DEBUG: Cleaned CSV Headers: " + csvHeaders);
        System.out.println("DEBUG: Total Mappings to check: " + mappingConfigs.size());

        for (MappingConfigModel mapping : mappingConfigs) {
            String targetName = mapping.getTargetAttribute() != null ? mapping.getTargetAttribute().getName() : "UNKNOWN";
            MappingType type = mapping.getMappingType();

            System.out.println(String.format("DEBUG: Checking Mapping -> Target: [%s], Type: [%s]", targetName, type));

            if (type == MappingType.DIRECT) {
                String source = mapping.getSourceAttribute();
                String normalizedSource = (source != null) ? source.trim().toLowerCase() : null;

                if (normalizedSource == null) {
                    System.out.println("ERROR: DIRECT mapping for [" + targetName + "] has a NULL source_attribute in DB!");
                    return false;
                }

                if (!csvHeaders.contains(normalizedSource)) {
                    System.out.println("ERROR: Column Match Failed!");
                    System.out.println("Expected Source Col: [" + normalizedSource + "]");
                    System.out.println("Actual CSV Cols: " + csvHeaders);
                    return false;
                }
                System.out.println("Match found for [" + normalizedSource + "]");
            }

            if (type == MappingType.TRANSFORMATION) {
                List<MappingExpressionModel> exprs = mapping.getExpressions();

                // Log the size to see if Hibernate actually loaded them
                System.out.println("DEBUG: Expressions found in list: " + (exprs == null ? "NULL" : exprs.size()));
                boolean hasActiveExpr = mapping.getExpressions() != null &&
                        mapping.getExpressions().stream().anyMatch(e -> e.isActive());
                if (!hasActiveExpr) {
                    System.out.println("ERROR: Transformation for [" + targetName + "] is missing an ACTIVE expression.");
                    return false;
                }
                System.out.println("Active expression found.");
            }
        }

        System.out.println("--- CSV VALIDATION SUCCESS ---");
        return true;
    }
}
