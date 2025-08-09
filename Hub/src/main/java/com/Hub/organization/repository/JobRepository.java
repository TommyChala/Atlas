package com.Hub.organization.repository;

import com.Hub.organization.model.JobModel;
import com.Hub.organization.model.OrganizationalUnitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobModel, UUID> {
    Optional<JobModel> findByExternalId(String externalId);
}
