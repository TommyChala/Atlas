package com.Hub.identity.model;

import com.Hub.organization.model.JobModel;
import com.Hub.organization.model.OrganizationalUnitModel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "Identity")
public class IdentityModel {

    @Id
    @GeneratedValue
    private UUID internalId;
    @Column(name = "IdentityId", unique = true, nullable = false)
    private String identityId;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Email")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationalUnitId", nullable = false)
    private OrganizationalUnitModel organizationalUnit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JobId", nullable = false)
    private JobModel job;
    @Column(name = "ManagerId")
    private String managerId;
    @Column (name = "IdentityStatus", nullable = false)
    private String identityStatus;
    @Column(name = "ValidFrom")
    private LocalDate validFrom;
    @Column(name = "ValidTo")
    private LocalDate validTo;

    public IdentityModel () {}

    public IdentityModel(String identityId, String firstName, String lastName, String email, OrganizationalUnitModel organizationalUnit, JobModel job, String managerId, String identityStatus, LocalDate validFrom, LocalDate validTo) {
        this.identityId = identityId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.organizationalUnit = organizationalUnit;
        this.job = job;
        this.managerId = managerId;
        this.identityStatus = identityStatus;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public UUID getInternalId() {
        return internalId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrganizationalUnitModel getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnitModel organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public JobModel getJob() {
        return job;
    }

    public void setJob(JobModel job) {
        this.job = job;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(String identityStatus) {
        this.identityStatus = identityStatus;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
