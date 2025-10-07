package com.Hub.account.mapper;

import com.Hub.account.dto.AccountAttributeModelCreateDTO;
import com.Hub.account.dto.AccountAttributeModelResponseDTO;
import com.Hub.account.enums.DataType;
import com.Hub.account.model.AccountAttributeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountAttributeModelMapper {

    default DataType mapStringToDataType(String dataType) {
        if (dataType == null) return null;
        return DataType.valueOf(dataType.toUpperCase()); // case-insensitive
    }

    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(source = "dataType", target = "dataType")
    public AccountAttributeModel toAccountAttributeModel (AccountAttributeModelCreateDTO accountAttributeModelCreateDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "displayName", target = "displayName")
    @Mapping(target = "dataType", expression = "java(accountAttributeModel.getDataType().name())")
    public AccountAttributeModelResponseDTO toResponseDTO (AccountAttributeModel accountAttributeModel);
}
