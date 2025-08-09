package com.Hub.identity.dto;

import java.time.LocalDate;

public class IdentityCreateDTO {

    private String identityId;
    private String firstName;
    private String lastName;
    private String email;
    private String organizationalUnitId;
    private String jobId;
    private String identityStatus;
    private LocalDate validFrom;
    private LocalDate validTo;

    public IdentityCreateDTO () {}

    public IdentityCreateDTO(String identityId, String firstName, String lastName, String email, String organizationalUnitId, String jobId, String identityStatus, LocalDate validFrom, LocalDate validTo) {
        this.identityId = identityId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.organizationalUnitId = organizationalUnitId;
        this.jobId = jobId;
        this.identityStatus = identityStatus;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityID(String identityID) {
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

    public String getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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
