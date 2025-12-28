package com.Hub.system.dto;

import java.util.List;
import java.util.Map;

public record CsvReadResult(
        String[] headers,
        List<Map<String, String>> dataRows
) {
}
