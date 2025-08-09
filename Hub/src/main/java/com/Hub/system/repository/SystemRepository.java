package com.Hub.system.repository;

import com.Hub.system.model.SystemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SystemRepository extends JpaRepository<SystemModel, UUID> {
}
