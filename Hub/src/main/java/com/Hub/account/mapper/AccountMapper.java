package com.Hub.account.mapper;

import com.Hub.account.dto.AccountModelCreateDTO;
import com.Hub.account.dto.AccountModelResponseDTO;
import com.Hub.account.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "accountName", target = "accountName")
    @Mapping(source = "accountId", target = "accountId")
    public AccountModelResponseDTO toResponseDTO (AccountModel accountModel);

    @Mapping(source = "accountId", target = "accountId")
    @Mapping(source = "accountName", target = "accountName")
    public AccountModel toAccountModel (AccountModelCreateDTO accountModelCreateDTO);
}
