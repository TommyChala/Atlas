package com.Hub.system.repository;

import com.Hub.system.model.MappingConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMappingConfigModelRepository extends JpaRepository<MappingConfigModel, UUID> {
}
