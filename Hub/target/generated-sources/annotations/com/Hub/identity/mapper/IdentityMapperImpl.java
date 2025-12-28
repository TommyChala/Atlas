package com.Hub.identity.mapper;

import com.Hub.identity.dto.IdentityCreateDTO;
import com.Hub.identity.dto.IdentityModelRequest;
import com.Hub.identity.model.IdentityModel;
import com.Hub.organization.model.JobModel;
import com.Hub.organization.model.OrganizationalUnitModel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T18:38:51+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class IdentityMapperImpl implements IdentityMapper {

    @Override
    public IdentityModelRequest IdentityToDTO(IdentityModel identityModel) {
        if ( identityModel == null ) {
            return null;
        }

        String identityId = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String organizationalUnitName = null;
        String jobId = null;
        String managerId = null;
        String identityStatus = null;
        LocalDate validFrom = null;
        LocalDate validTo = null;

        identityId = identityModel.getIdentityId();
        firstName = identityModel.getFirstName();
        lastName = identityModel.getLastName();
        email = identityModel.getEmail();
        organizationalUnitName = identityModelOrganizationalUnitName( identityModel );
        jobId = identityModelJobExternalId( identityModel );
        managerId = identityModel.getManagerId();
        identityStatus = identityModel.getIdentityStatus();
        validFrom = identityModel.getValidFrom();
        validTo = identityModel.getValidTo();

        IdentityModelRequest identityModelRequest = new IdentityModelRequest( identityId, firstName, lastName, email, organizationalUnitName, jobId, managerId, identityStatus, validFrom, validTo );

        return identityModelRequest;
    }

    @Override
    public IdentityModel CreateDTOToIdentity(IdentityCreateDTO identityCreateDTO) {
        if ( identityCreateDTO == null ) {
            return null;
        }

        IdentityModel identityModel = new IdentityModel();

        identityModel.setIdentityId( identityCreateDTO.getIdentityId() );
        identityModel.setFirstName( identityCreateDTO.getFirstName() );
        identityModel.setLastName( identityCreateDTO.getLastName() );
        identityModel.setEmail( identityCreateDTO.getEmail() );
        identityModel.setIdentityStatus( identityCreateDTO.getIdentityStatus() );
        identityModel.setValidFrom( identityCreateDTO.getValidFrom() );
        identityModel.setValidTo( identityCreateDTO.getValidTo() );

        return identityModel;
    }

    private String identityModelOrganizationalUnitName(IdentityModel identityModel) {
        if ( identityModel == null ) {
            return null;
        }
        OrganizationalUnitModel organizationalUnit = identityModel.getOrganizationalUnit();
        if ( organizationalUnit == null ) {
            return null;
        }
        String name = organizationalUnit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String identityModelJobExternalId(IdentityModel identityModel) {
        if ( identityModel == null ) {
            return null;
        }
        JobModel job = identityModel.getJob();
        if ( job == null ) {
            return null;
        }
        String externalId = job.getExternalId();
        if ( externalId == null ) {
            return null;
        }
        return externalId;
    }
}
