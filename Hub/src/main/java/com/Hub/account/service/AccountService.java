package com.Hub.account.service;

import com.Hub.account.model.AccountModel;
import com.Hub.account.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService (IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountModel> findAll() {
           return accountRepository.findAll();
    }

    public AccountModel createNew(AccountModel accountModel) {
        return accountRepository.save(accountModel);
    }
}
