package com.Hub.system.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class CsvExtractor {

    public Stream<Map<String, String>> extract (Path csvFile) throws IOException {
        List<String> lines = Files.readAllLines(csvFile);

        if (lines.isEmpty()) {
            return Stream.empty();
        }

        String[] headers = lines.get(0).split(";");
        return lines.stream()
                .skip(1)
                .map(line -> parseLine(line, headers));
    }

    private Map<String, String> parseLine (String line, String[] headers) {
        String[] parts = line.split(";");
        Map<String, String> row = new HashMap<>();
        for (int i = 0; i < headers.length && i < parts.length; i++) {
            row.put(headers[i], parts[i]);
        }
        return row;
    }
}
