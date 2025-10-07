package com.Hub.account.repository;

import com.Hub.account.model.AccountAttributeValueModel;
import com.Hub.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAccountAttributeValueRepository extends JpaRepository<AccountAttributeValueModel, UUID> {
    Optional<AccountAttributeValueModel> findByAccountAndAttribute_Name(AccountModel account, String attributeName);
}
