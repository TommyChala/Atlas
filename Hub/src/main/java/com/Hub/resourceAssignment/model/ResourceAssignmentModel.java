package com.Hub.resourceAssignment.model;

import com.Hub.account.model.AccountModel;
import com.Hub.resource.model.ResourceModel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "ResourceAssignment",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"account_id", "resource_id"})
    }
)
public class ResourceAssignmentModel {

    @Id
    @GeneratedValue
    private UUID Id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountId")
    private AccountModel account;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resourceId")
    private ResourceModel resource;

    @Column(name = "compliance_state", nullable = false)
    private String complianceState;

    @Column(name = "validFrom", nullable = false)
    private LocalDate validFrom;

    @Column(name = "validTo", nullable = false)
    private LocalDate validTo;


    public ResourceAssignmentModel () {}

    public ResourceAssignmentModel (UUID Id, AccountModel account, ResourceModel resource, String complianceState, LocalDate validFrom, LocalDate validTo) {
        this.account = account;
        this.resource = resource;
        this.complianceState = complianceState;
        this.validFrom = validFrom;
        this.validTo = validTo;

    }


    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public ResourceModel getResource() {
        return resource;
    }

    public void setResource(ResourceModel resource) {
        this.resource = resource;
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


