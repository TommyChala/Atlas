package com.Hub.system.model;

import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import jakarta.persistence.Entity;

import java.nio.file.Path;

public class CollectorSource {
    private Path file;
    private String apiEndpoint;
    private Object ldapConnection;
    private Long systemId;
    private CollectorType collectorType;
    private EntityType entityType;

    public CollectorSource () {}

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public Object getLdapConnection() {
        return ldapConnection;
    }

    public void setLdapConnection(Object ldapConnection) {
        this.ldapConnection = ldapConnection;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public CollectorType getCollectorType() {
        return collectorType;
    }

    public void setCollectorType(CollectorType collectorType) {
        this.collectorType = collectorType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
