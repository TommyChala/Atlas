package com.Hub.system.strategy;

import com.Hub.system.model.CollectorSource;
import com.Hub.system.service.CsvExtractor;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public class CsvCollectorStrategy implements CollectorStrategy {

    private final CsvExtractor extractor;

    public CsvCollectorStrategy (CsvExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public Stream<Map<String, String>> collect(CollectorSource source) throws Exception {
        Path file = source.getFile();
        return extractor.extract(file);
    }
}
