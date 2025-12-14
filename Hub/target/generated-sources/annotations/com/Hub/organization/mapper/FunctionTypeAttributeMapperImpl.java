package com.Hub.organization.mapper;

import com.Hub.organization.dto.FunctionTypeAttributeCreateDTO;
import com.Hub.organization.dto.FunctionTypeAttributeResponseDTO;
import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.model.FunctionTypeModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-07T19:14:13+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class FunctionTypeAttributeMapperImpl implements FunctionTypeAttributeMapper {

    @Override
    public FunctionTypeAttributeModel toModel(FunctionTypeAttributeCreateDTO functionTypeAttributeCreateDTO) {
        if ( functionTypeAttributeCreateDTO == null ) {
            return null;
        }

        FunctionTypeAttributeModel functionTypeAttributeModel = new FunctionTypeAttributeModel();

        functionTypeAttributeModel.setIsRequired( functionTypeAttributeCreateDTO.isRequired() );
        functionTypeAttributeModel.setDataType( functionTypeAttributeCreateDTO.dataType() );
        functionTypeAttributeModel.setName( functionTypeAttributeCreateDTO.name() );

        return functionTypeAttributeModel;
    }

    @Override
    public FunctionTypeAttributeResponseDTO toDTO(FunctionTypeAttributeModel functionTypeAttributeModel) {
        if ( functionTypeAttributeModel == null ) {
            return null;
        }

        String name = null;
        boolean isRequired = false;
        String functionType = null;

        name = functionTypeAttributeModel.getName();
        if ( functionTypeAttributeModel.getIsRequired() != null ) {
            isRequired = functionTypeAttributeModel.getIsRequired();
        }
        functionType = functionTypeAttributeModelFunctionTypeName( functionTypeAttributeModel );

        FunctionTypeAttributeResponseDTO functionTypeAttributeResponseDTO = new FunctionTypeAttributeResponseDTO( name, functionType, isRequired );

        return functionTypeAttributeResponseDTO;
    }

    private String functionTypeAttributeModelFunctionTypeName(FunctionTypeAttributeModel functionTypeAttributeModel) {
        if ( functionTypeAttributeModel == null ) {
            return null;
        }
        FunctionTypeModel functionType = functionTypeAttributeModel.getFunctionType();
        if ( functionType == null ) {
            return null;
        }
        String name = functionType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
