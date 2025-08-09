package com.Hub.organization.repository;

import com.Hub.organization.model.OrganizationalUnitHierarchyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationalUnitHierarchyRepository extends JpaRepository<OrganizationalUnitHierarchyModel, UUID> {
}
