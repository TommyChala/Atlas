package com.Hub.organization.model;

import com.Hub.identity.model.IdentityModel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Job")
public class JobModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "level")
    private String level;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<IdentityModel> identities = new ArrayList<>();

    @Column(name = "validTo", nullable = false)
    private LocalDate validTo;

    @Column(name = "whenCreated", nullable = false)
    private LocalDate whenCreated;

    @Column(name = "lastUpdated")
    private LocalDate lastUpdated;

    public JobModel() {
    }

    public JobModel(UUID id, String externalId, String name, String level, LocalDate validTo, LocalDate whenCreated, LocalDate lastUpdated) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.level = level;
        this.validTo = validTo;
        this.whenCreated = whenCreated;
        this.lastUpdated = lastUpdated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<IdentityModel> getIdentities() {
        return identities;
    }

    public void setIdentities(List<IdentityModel> identities) {
        this.identities = identities;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public LocalDate getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(LocalDate whenCreated) {
        this.whenCreated = whenCreated;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}


