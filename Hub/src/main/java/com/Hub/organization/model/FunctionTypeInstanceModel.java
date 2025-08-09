package com.Hub.organization.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "function_type_instance")
public class FunctionTypeInstanceModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "function_type_id", nullable = false)
    private FunctionTypeModel functionType;

    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FunctionTypeAttributeValueModel> attributeValues = new ArrayList<>();

    public FunctionTypeInstanceModel () {}

    public FunctionTypeInstanceModel(UUID id, String name, FunctionTypeModel functionType, List<FunctionTypeAttributeValueModel> attributeValues) {
        this.id = id;
        this.name = name;
        this.functionType = functionType;
        this.attributeValues = attributeValues;
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

    public FunctionTypeModel getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionTypeModel functionType) {
        this.functionType = functionType;
    }

    public List<FunctionTypeAttributeValueModel> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<FunctionTypeAttributeValueModel> attributeValues) {
        this.attributeValues = attributeValues;
    }
}

