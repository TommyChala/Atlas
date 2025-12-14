package com.Hub.system.repository;

import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IMappingConfigModelRepository extends JpaRepository<MappingConfigModel, UUID> {
    List<MappingConfigModel> findBySystemOrSystemIsNull(SystemModel system);

}

