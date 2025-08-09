package com.Hub.resource.repository;

import com.Hub.resource.model.ResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IResourceRepository extends JpaRepository<ResourceModel, UUID> {
    Optional<ResourceModel> findByResourceId(String resourceId);
}
