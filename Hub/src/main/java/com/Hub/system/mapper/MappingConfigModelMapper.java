package com.Hub.system.mapper;

import com.Hub.account.enums.DataType;
import com.Hub.system.dto.MappingConfigModelCreateDTO;
import com.Hub.system.enums.MappingConfigDataType;
import com.Hub.system.model.MappingConfigModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MappingConfigModelMapper {

    default MappingConfigDataType mapStringToDataType(String dataType) {
        if (dataType == null) return null;
        return MappingConfigDataType.valueOf(dataType.toUpperCase()); // case-insensitive
    }

    @Mapping(source = "sourceAttribute", target = "sourceAttribute")
    @Mapping(target = "targetAttribute", ignore = true)
    @Mapping(source = "dataType", target = "dataType")
    public MappingConfigModel toModel (MappingConfigModelCreateDTO mappingConfigModelCreateDTO);
}
