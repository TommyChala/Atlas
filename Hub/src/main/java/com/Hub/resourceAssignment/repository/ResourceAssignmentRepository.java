package com.Hub.resourceAssignment.repository;

import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceAssignmentRepository extends JpaRepository<ResourceAssignmentModel, UUID> {
}
