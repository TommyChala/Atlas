package com.Hub.organization.model;

import com.Hub.identity.model.IdentityModel;
import jakarta.persistence.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "OrganizationalUnit")
public class OrganizationalUnitModel {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;
    @Column(name = "external_id", unique = true, nullable = false)
    private String externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "validTo", nullable = false)
    private LocalDate validTo;

    @Column(name = "whenCreated", nullable = false)
    private LocalDate whenCreated;

    @Column(name = "lastUpdated")
    private LocalDate lastUpdated;

    @OneToMany(mappedBy = "organizationalUnit", cascade = CascadeType.ALL)
    private List<IdentityModel> identities = new ArrayList<>();

    public OrganizationalUnitModel() {
    }

    public OrganizationalUnitModel(UUID id, String externalId, String name, LocalDate validTo, LocalDate whenCreated, LocalDate lastUpdated) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
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

    public List<IdentityModel> getIdentities() {
        return identities;
    }

    public void setIdentities(List<IdentityModel> identities) {
        this.identities = identities;
    }
}




