package com.Hub.organization.repository;

import com.Hub.organization.model.FunctionTypeAttributeValueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FunctionTypeAttributeValueRepository extends JpaRepository<FunctionTypeAttributeValueModel, UUID> {
}
