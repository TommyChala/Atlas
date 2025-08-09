package com.Hub.organization.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "FunctionTypeAttributeValue")
public class FunctionTypeAttributeValueModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "instance_id", nullable = false)
    private FunctionTypeInstanceModel instance;

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    private FunctionTypeAttributeModel attribute;

    @Column(name = "value", nullable = false)
    private String value;

    public FunctionTypeAttributeValueModel () {}

    public FunctionTypeAttributeValueModel(UUID id, FunctionTypeInstanceModel instance, FunctionTypeAttributeModel attribute, String value) {
        this.id = id;
        this.instance = instance;
        this.attribute = attribute;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FunctionTypeInstanceModel getInstance() {
        return instance;
    }

    public void setInstance(FunctionTypeInstanceModel instance) {
        this.instance = instance;
    }

    public FunctionTypeAttributeModel getAttribute() {
        return attribute;
    }

    public void setAttribute(FunctionTypeAttributeModel attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}