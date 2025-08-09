package com.Hub.organization.repository;

import com.Hub.organization.model.OrganizationalUnitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnitModel, UUID> {
    Optional<OrganizationalUnitModel> findByExternalId(String externalId);
}
