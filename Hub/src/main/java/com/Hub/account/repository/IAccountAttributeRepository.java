package com.Hub.account.repository;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.system.model.SystemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountAttributeRepository extends JpaRepository<AccountAttributeModel, UUID> {
    Optional<AccountAttributeModel> findByName (String name);

    List<AccountAttributeModel> findBySystemOrSystemIsNull(SystemModel system);
}
