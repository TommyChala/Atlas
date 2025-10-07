package com.Hub.account.model;

import com.Hub.account.enums.DataType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accountAttributeModel")
public class AccountAttributeModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "displayName")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "dataType", nullable = false)
    private DataType dataType;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountAttributeValueModel> values = new ArrayList<>();

    public AccountAttributeModel () {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public List<AccountAttributeValueModel> getValues() {
        return values;
    }

    public void setValues(List<AccountAttributeValueModel> values) {
        this.values = values;
    }
}
