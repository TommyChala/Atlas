package com.Hub.organization.mapper;

import com.Hub.organization.dto.OrganizationalUnitCreateDTO;
import com.Hub.organization.model.OrganizationalUnitModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-22T15:20:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class OrganizationalUnitMapperImpl implements OrganizationalUnitMapper {

    @Override
    public OrganizationalUnitModel toModel(OrganizationalUnitCreateDTO organizationalUnitCreateDTO) {
        if ( organizationalUnitCreateDTO == null ) {
            return null;
        }

        OrganizationalUnitModel organizationalUnitModel = new OrganizationalUnitModel();

        organizationalUnitModel.setExternalId( organizationalUnitCreateDTO.externalId() );
        organizationalUnitModel.setName( organizationalUnitCreateDTO.name() );
        organizationalUnitModel.setValidTo( organizationalUnitCreateDTO.validTo() );

        return organizationalUnitModel;
    }
}
