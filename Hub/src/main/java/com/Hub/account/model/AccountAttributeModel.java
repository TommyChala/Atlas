package com.Hub.account.model;

import com.Hub.account.enums.DataType;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
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

    @Column(name = "required", nullable = false)
    private boolean required;

    @ManyToOne
    @JoinColumn(name = "system")
    private SystemModel system;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountAttributeValueModel> values = new ArrayList<>();

    @OneToMany(mappedBy = "targetAttribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MappingConfigModel> sourceAttributes = new ArrayList<>();

    public AccountAttributeModel () {}

    public AccountAttributeModel (String name, String displayName, DataType dataType, boolean required) {
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
        this.required = required;
    }

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<MappingConfigModel> getSourceAttributes() {
        return sourceAttributes;
    }

    public void setSourceAttributes(List<MappingConfigModel> sourceAttributes) {
        this.sourceAttributes = sourceAttributes;
    }

    public SystemModel getSystem() {
        return system;
    }

    public void setSystem(SystemModel system) {
        this.system = system;
    }
}
