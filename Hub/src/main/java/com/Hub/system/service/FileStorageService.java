package com.Hub.system.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * Stores the uploaded MultipartFile to a stable location (temp directory)
     * and returns the absolute path/key. This runs synchronously within the Controller thread.
     */
    public String storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty.");
        }

        // 1. Create a unique file name and path
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File tempFile = new File(TEMP_DIR, fileName);

        // 2. Transfer the uploaded data to the stable temporary file location
        multipartFile.transferTo(tempFile);

        // 3. Return the stable path/key for the asynchronous job
        return tempFile.getAbsolutePath();
    }

    public File retrieveFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Collection source file not found at path: " + filePath);
        }
        return file;
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }
}

