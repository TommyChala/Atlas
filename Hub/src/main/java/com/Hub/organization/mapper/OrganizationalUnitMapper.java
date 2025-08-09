package com.Hub.organization.mapper;

import com.Hub.identity.dto.IdentityCreateDTO;
import com.Hub.organization.dto.OrganizationalUnitCreateDTO;
import com.Hub.organization.model.OrganizationalUnitModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationalUnitMapper {
    @Mapping(target = "externalId", source = "externalId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "validTo", source = "validTo")
    public OrganizationalUnitModel toModel (OrganizationalUnitCreateDTO organizationalUnitCreateDTO);

}
