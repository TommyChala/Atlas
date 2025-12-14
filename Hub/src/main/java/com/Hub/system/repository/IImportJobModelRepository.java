package com.Hub.system.repository;

import com.Hub.system.model.ImportJobModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IImportJobModelRepository extends JpaRepository<ImportJobModel, Long> {
}
