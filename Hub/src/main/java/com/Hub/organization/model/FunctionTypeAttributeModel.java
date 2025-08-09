package com.Hub.organization.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "FunctionTypeAttribute")
public class FunctionTypeAttributeModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dataType", nullable = false)
    private String dataType;

    @ManyToOne
    @JoinColumn(name = "function_type_id", nullable = false)
    private FunctionTypeModel functionType;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FunctionTypeAttributeValueModel> values = new ArrayList<>();

    @Column(name = "IsRequired", nullable = false)
    private Boolean isRequired;

    public FunctionTypeAttributeModel () {}

    public FunctionTypeAttributeModel(UUID id, String name, String dataType, FunctionTypeModel functionType, List<FunctionTypeAttributeValueModel> values, Boolean isRequired) {
        this.id = id;
        this.name = name;
        this.dataType = dataType;
        this.functionType = functionType;
        this.values = values;
        this.isRequired = isRequired;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public FunctionTypeModel getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionTypeModel functionType) {
        this.functionType = functionType;
    }

    public List<FunctionTypeAttributeValueModel> getValues() {
        return values;
    }

    public void setValues(List<FunctionTypeAttributeValueModel> values) {
        this.values = values;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
}
