package com.Hub.organization.mapper;

import com.Hub.organization.dto.FunctionTypeAttributeCreateDTO;
import com.Hub.organization.dto.FunctionTypeAttributeResponseDTO;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FunctionTypeAttributeMapper {
    //@Mapping(source = "functionTypeId", target = "functionType")
    @Mapping(source = "isRequired", target = "isRequired")
    @Mapping(source = "dataType", target = "dataType")
    @Mapping(source = "name", target = "name")
    public FunctionTypeAttributeModel toModel (FunctionTypeAttributeCreateDTO functionTypeAttributeCreateDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "isRequired", target = "isRequired")
    @Mapping(source = "functionType.name", target = "functionType")
    public FunctionTypeAttributeResponseDTO toDTO (FunctionTypeAttributeModel functionTypeAttributeModel);
}

