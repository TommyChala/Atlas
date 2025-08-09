package com.Hub.resourceAssignment.mapper;

import com.Hub.resourceAssignment.dto.ResourceAssignmentCreateDTO;
import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-22T15:20:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ResourceAssignmentMapperImpl implements ResourceAssignmentMapper {

    @Override
    public ResourceAssignmentModel CreateDTOToIdentity(ResourceAssignmentCreateDTO createRequest) {
        if ( createRequest == null ) {
            return null;
        }

        ResourceAssignmentModel resourceAssignmentModel = new ResourceAssignmentModel();

        resourceAssignmentModel.setComplianceState( createRequest.getComplianceState() );
        resourceAssignmentModel.setValidFrom( createRequest.getValidFrom() );
        resourceAssignmentModel.setValidTo( createRequest.getValidTo() );

        return resourceAssignmentModel;
    }
}
