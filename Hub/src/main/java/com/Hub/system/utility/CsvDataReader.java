package com.Hub.system.utility;

import com.Hub.system.dto.CsvReadResult;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class CsvDataReader {


    public String[] readHeaders(File file) throws IOException, CsvValidationException {
        try (Reader reader = new FileReader(file);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] rawHeaders = csvReader.readNext();
            if (rawHeaders == null) return new String[0];

            return CSVFileValidator.getCleanedUpHeaders(rawHeaders);
        }
    }

    public void streamCsv(File file, String[] cleanedHeaders, Consumer<Map<String, String>> rowConsumer)
            throws IOException, CsvValidationException {

        try (Reader reader = new FileReader(file);
             CSVReader csvReader = new CSVReader(reader)) {

            // Skip the first line (headers) because we already processed them
            csvReader.readNext();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < cleanedHeaders.length; i++) {
                    // Map the current line to the headers
                    String value = (i < line.length) ? line[i] : null;
                    row.put(cleanedHeaders[i].toLowerCase(), value);
                }

                // PUSH the row out to the processor
                rowConsumer.accept(row);
            }
        }
    }
}

    /*
    public CsvReadResult readCsv (File file) throws IOException, CsvValidationException {

        try (Reader reader = new FileReader(file);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] rawHeaders = csvReader.readNext();
            if (rawHeaders == null) {
                return new CsvReadResult(new String[0], List.of());
            }

            String[] cleanedHeaders = CSVFileValidator.getCleanedUpHeaders(rawHeaders);

            List<Map<String, String>> dataRows = new ArrayList<>();
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < cleanedHeaders.length; i++) {
                    if (i < line.length) {
                        row.put(cleanedHeaders[i].toLowerCase(), line[i]);
                    } else {
                        row.put(cleanedHeaders[i].toLowerCase(), null);
                    }
                }

                dataRows.add(row);
            }
            return new CsvReadResult(cleanedHeaders, dataRows);
        }
    }

     */

