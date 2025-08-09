package com.Hub.organization.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "OrganizationalUnitHierarchy")
public class OrganizationalUnitHierarchyModel {
    @Id
    private UUID id;
    private UUID parentId;
}
