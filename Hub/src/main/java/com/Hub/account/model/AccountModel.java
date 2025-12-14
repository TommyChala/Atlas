package com.Hub.account.model;

import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import com.Hub.system.model.SystemModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account",
        uniqueConstraints = @UniqueConstraint(columnNames = "accountId")
)
public class AccountModel {

    @Id
    @Column(name = "uid", nullable = false, updatable = false)
    @GeneratedValue
    private UUID uid;

    @Column(name = "accountId")
    private String accountId;

    @ManyToOne
    @JoinColumn(name = "systemId")
    private SystemModel system;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountAttributeValueModel> values = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceAssignmentModel> assignment = new ArrayList<>();

    public AccountModel () {}

    public AccountModel (UUID Uid, String AccountId, SystemModel system) {
        this.uid = Uid;
        this.accountId = AccountId;
        this.system = system;
    }

    public UUID getUid() {
        return uid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public SystemModel getSystem () {
        return system;
    }

    public void setSystem (SystemModel system) {
        this.system = system;
    }

    public List<AccountAttributeValueModel> getValues() {
        return values;
    }

    public void setValues(List<AccountAttributeValueModel> values) {
        this.values = values;
    }

    public List<ResourceAssignmentModel> getAssignment() {
        return assignment;
    }

    public void setAssignment(List<ResourceAssignmentModel> assignment) {
        this.assignment = assignment;
    }
}
