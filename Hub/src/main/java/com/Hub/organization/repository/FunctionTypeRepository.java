package com.Hub.organization.repository;

import com.Hub.organization.model.FunctionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionTypeRepository extends JpaRepository<FunctionTypeModel, Integer> {
    Optional<FunctionTypeModel> findByName(String name);
}
