package com.Hub.account.model;

import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import com.Hub.system.model.SystemModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Account",
        uniqueConstraints = @UniqueConstraint(columnNames = "accountId")
)
public class AccountModel {

    @Id
    @Column(name = "uid", nullable = false, unique = true, updatable = false)
    @GeneratedValue
    private UUID uid;
    @Column(name = "accountId")
    private String accountId;
    @Column(name = "accountName")
    private String accountName;
    @ManyToOne
    @JoinColumn(name = "SystemId")
    private SystemModel system;
    @OneToMany(mappedBy = "account")
    private List<ResourceAssignmentModel> assignment;

    public AccountModel () {}

    public AccountModel (UUID Uid, String AccountId, String AccountName, SystemModel system) {
        this.uid = Uid;
        this.accountId = AccountId;
        this.accountName = AccountName;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public SystemModel getSystem () {
        return system;
    }

    public void setSystem (SystemModel system) {
        this.system = system;
    }
}
