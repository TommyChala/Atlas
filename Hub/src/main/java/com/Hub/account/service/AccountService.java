package com.Hub.account.service;

import com.Hub.account.dto.AccountModelCreateDTO;
import com.Hub.account.model.AccountModel;
import com.Hub.account.repository.IAccountRepository;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;
    private final SystemRepository systemRepository;

    public AccountService(IAccountRepository accountRepository, SystemRepository systemRepository) {
        this.accountRepository = accountRepository;
        this.systemRepository = systemRepository;
    }

    public List<AccountModel> findAll() {
        return accountRepository.findAll();
    }

    public AccountModel createNew(AccountModelCreateDTO accountModelCreateDTO) {
        AccountModel account = new AccountModel();
        account.setAccountId(accountModelCreateDTO.accountId());
        account.setAccountName(accountModelCreateDTO.accountName());
        SystemModel system = systemRepository.findBySystemId(accountModelCreateDTO.systemId())
                .orElseThrow(() -> new RuntimeException("Unable to create account. No system found with referenced id")
                );
        account.setSystem(system);
        return accountRepository.save(account);
    }
}

