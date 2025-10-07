package com.Hub.system.model;

import com.Hub.account.model.AccountModel;
import com.Hub.resource.model.ResourceModel;
import com.Hub.system.enums.Protocol;
import com.Hub.system.enums.SystemType;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "systems")
public class SystemModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "systemId", nullable = false, unique = true)
    private Long systemId;

    @Column(name = "systemName", nullable = false)
    private String name;

    @Column(name = "systemType")
    private SystemType type;

    @Column(name = "systemProtocol")
    private Protocol protocol;

    @Column(name = "LastImportStart")
    private LocalDateTime lastImportStart;

    @Column(name = "LastImportFinish")
    private LocalDateTime lastImportFinish;

    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountModel> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceModel> resources = new ArrayList<>();

    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MappingConfigModel> mappingConfigModels = new ArrayList<>();

    public SystemModel () {}

    public SystemModel(UUID id, String name, SystemType type, Protocol protocol, Long systemId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.protocol = protocol;
        this.systemId = systemId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SystemType getType() {
        return type;
    }

    public void setType(SystemType type) {
        this.type = type;
    }

    public Protocol getProtocol () {
        return protocol;
    }

    public void setProtocol (Protocol protocol) {
        this.protocol = protocol;
    }

    public List<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public List<ResourceModel> getResources() {
        return resources;
    }

    public void setResources(List<ResourceModel> resources) {
        this.resources = resources;
    }
}
