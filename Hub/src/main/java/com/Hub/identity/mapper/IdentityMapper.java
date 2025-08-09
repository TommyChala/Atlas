package com.Hub.identity.mapper;

import com.Hub.identity.dto.IdentityCreateDTO;
import com.Hub.identity.dto.IdentityModelRequest;
import com.Hub.identity.model.IdentityModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    @Mapping(target = "identityId", source = "identityId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "organizationalUnitName", source = "organizationalUnit.name")
    @Mapping(target = "jobId", source = "job.externalId")
    @Mapping(target = "managerId", source = "managerId")
    @Mapping(target = "identityStatus", source = "identityStatus")
    @Mapping(target = "validFrom", source = "validFrom")
    @Mapping(target = "validTo", source = "validTo")
    IdentityModelRequest IdentityToDTO(IdentityModel identityModel);

    @Mapping(target = "identityId", source = "identityId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "identityStatus", source = "identityStatus")
    @Mapping(target = "validFrom", source = "validFrom")
    @Mapping(target = "validTo", source = "validTo")
    IdentityModel CreateDTOToIdentity (IdentityCreateDTO identityCreateDTO);



}
