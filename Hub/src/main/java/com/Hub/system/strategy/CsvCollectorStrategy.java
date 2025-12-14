package com.Hub.system.strategy;

import com.Hub.system.dto.CollectorSourceCreateRequest;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.service.CsvExtractor;
import com.Hub.system.utility.CsvParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class CsvCollectorStrategy implements CollectorStrategy<MultipartFile> {

    private final CsvExtractor extractor;
    private final CsvParser csvParser;

    public CsvCollectorStrategy (CsvExtractor extractor, CsvParser csvParser) {
        this.extractor = extractor;
        this.csvParser = csvParser;
    }
    @Override
    public CollectorType getType() {
        return CollectorType.CSV;
    }

    @Override
    public void collect(CollectorSourceCreateRequest source) {

        if (source.entityType() == EntityType.ACCOUNT) {
            try {
                csvParser.parseAccounts(source.file(), source.systemId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
