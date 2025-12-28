package com.Hub.system.repository;

import com.Hub.system.model.MappingExpressionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMappingExpressionRepository extends JpaRepository<MappingExpressionModel, UUID> {
}
