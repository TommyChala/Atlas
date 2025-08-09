package com.Hub.resourceAssignment.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.Hub.resourceAssignment.dto.ResourceAssignmentCreateDTO;
import com.Hub.resourceAssignment.model.ResourceAssignmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.transform.Source;

@Mapper(componentModel = "spring")
public interface ResourceAssignmentMapper {
    @Mapping(source = "complianceState", target = "complianceState")
    @Mapping(source = "validFrom", target = "validFrom")
    @Mapping(source = "validTo", target = "validTo")
    ResourceAssignmentModel CreateDTOToIdentity (ResourceAssignmentCreateDTO createRequest);
}
