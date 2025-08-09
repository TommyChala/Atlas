package com.Hub.identity.repository;

import com.Hub.identity.model.IdentityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdentityRepository extends JpaRepository<IdentityModel, UUID> {
}
