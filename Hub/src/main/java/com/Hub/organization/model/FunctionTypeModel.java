package com.Hub.organization.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "function_type")
public class FunctionTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name ="systemDefined", nullable = false)
    private Boolean systemDefined;

    @OneToMany(mappedBy = "functionType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FunctionTypeAttributeModel> attributes = new ArrayList<>();

    public FunctionTypeModel () {}

    public FunctionTypeModel(int id, String name, Boolean systemDefined, List<FunctionTypeAttributeModel> attributes) {
        this.id = id;
        this.name = name;
        this.systemDefined = systemDefined;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSystemDefined() {
        return systemDefined;
    }

    public void setSystemDefined(Boolean systemDefined) {
        this.systemDefined = systemDefined;
    }

    public List<FunctionTypeAttributeModel> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<FunctionTypeAttributeModel> attributes) {
        this.attributes = attributes;
    }
}
