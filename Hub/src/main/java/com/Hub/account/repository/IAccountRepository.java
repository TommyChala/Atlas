package com.Hub.account.repository;

import com.Hub.account.model.AccountModel;
import com.Hub.resource.model.ResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<AccountModel, UUID> {
    Optional<AccountModel> findByAccountId (String accountId);
    //Optional<AccountModel> findByAccountName (String accountName);

}
