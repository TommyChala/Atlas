package com.Hub.system.utility;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.IMappingConfigModelRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    public static boolean validateHeadersAgainstMapping (String[] headers, List<MappingConfigModel> mappingConfigModel) {

        Set<String> csvHeaders = Arrays.stream(headers)
                .map(h -> h.replace("\uFEFF", ""))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> validAttributes = mappingConfigModel.stream()
                .map(MappingConfigModel::getSourceAttribute)
                .map(attr -> attr.trim().toLowerCase())
                .collect(Collectors.toSet());

        return csvHeaders.containsAll(validAttributes);
    }

}
