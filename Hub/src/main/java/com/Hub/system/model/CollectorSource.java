package com.Hub.system.model;

import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import jakarta.persistence.Entity;

import java.nio.file.Path;

public abstract class CollectorSource {

    private String systemId;
    private String collectorTypeStr;
    private String entityTypeStr;

    public CollectorSource(String systemId, String collectorType, String entityTypeStr) {
        this.systemId = systemId;
        this.collectorTypeStr = collectorType;
        this.entityTypeStr = entityTypeStr;
    }

    public CollectorSource () {}

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCollectorTypeStr() {
        return collectorTypeStr;
    }

    public void setCollectorTypeStr(String collectorTypeStr) {
        this.collectorTypeStr = collectorTypeStr;
    }

    public String getEntityTypeStr() {
        return entityTypeStr;
    }

    public void setEntityTypeStr(String entityTypeStr) {
        this.entityTypeStr = entityTypeStr;
    }
}
