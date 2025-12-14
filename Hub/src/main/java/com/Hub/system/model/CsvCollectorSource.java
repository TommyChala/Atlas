package com.Hub.system.model;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public class CsvCollectorSource extends CollectorSource {

    private MultipartFile file;

    public CsvCollectorSource () {
        super();
    }

    public CsvCollectorSource (String systemId, String entityTypeStr, String collectorTypeStr, MultipartFile file) {
        super(systemId, entityTypeStr, collectorTypeStr);
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
