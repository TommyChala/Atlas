package com.Hub.resourceAssignment.dto;

import java.time.LocalDate;

public class ResourceAssignmentCreateDTO {

    private String accountId;
    private String resourceId;
    private String complianceState;
    private LocalDate validFrom;
    private LocalDate validTo;

    public ResourceAssignmentCreateDTO() {}

    public ResourceAssignmentCreateDTO (String accountId, String resourceId, String complianceState, LocalDate validFrom, LocalDate validTo) {
        this.accountId = accountId;
        this.resourceId = resourceId;
        this.complianceState = complianceState;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getComplianceState() {
        return complianceState;
    }

    public void setComplianceState(String complianceState) {
        this.complianceState = complianceState;
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
