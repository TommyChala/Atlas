package com.Hub.system.mapper;

import com.Hub.system.dto.MappingExpressionCreateDTO;
import com.Hub.system.model.MappingExpressionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MappingExpressionMapper {

    @Mapping(source = "expression", target = "expression")
    @Mapping(source = "description", target = "description")

    public MappingExpressionModel toModel (MappingExpressionCreateDTO mappingExpressionCreateDTO);
}
