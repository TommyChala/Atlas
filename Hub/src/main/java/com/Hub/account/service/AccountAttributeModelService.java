package com.Hub.account.service;

import com.Hub.account.dto.AccountAttributeModelCreateDTO;
import com.Hub.account.dto.AccountAttributeModelResponseDTO;
import com.Hub.account.mapper.AccountAttributeModelMapper;
import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountAttributeModelService {

    private final IAccountAttributeRepository accountAttributeRepository;
    private final AccountAttributeModelMapper accountAttributeModelMapper;

    public AccountAttributeModelService(IAccountAttributeRepository accountAttributeRepository, AccountAttributeModelMapper accountAttributeModelMapper) {
        this.accountAttributeRepository = accountAttributeRepository;
        this.accountAttributeModelMapper = accountAttributeModelMapper;
    }

    public AccountAttributeModelResponseDTO addNew (AccountAttributeModelCreateDTO accountAttributeModelCreateDTO) {
        AccountAttributeModel accountAttribute = accountAttributeModelMapper.toAccountAttributeModel(accountAttributeModelCreateDTO);
        accountAttributeRepository.save(accountAttribute);
        return accountAttributeModelMapper.toResponseDTO(accountAttribute);
    }
}
